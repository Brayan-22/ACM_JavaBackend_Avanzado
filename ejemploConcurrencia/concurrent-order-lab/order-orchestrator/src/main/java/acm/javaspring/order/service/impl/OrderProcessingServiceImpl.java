package acm.javaspring.order.service.impl;

import acm.javaspring.order.client.CustomerValidationClient;
import acm.javaspring.order.client.InventoryClient;
import acm.javaspring.order.client.PaymentClient;
import acm.javaspring.order.model.OrderRecord;
import acm.javaspring.order.service.OrderProcessingService;
import acm.javaspring.order.store.OrderStateStore;
import acm.javaspring.shared.dto.customer.CustomerValidationResponse;
import acm.javaspring.shared.dto.inventory.InventoryResponse;
import acm.javaspring.shared.dto.order.OrderRequest;
import acm.javaspring.shared.dto.order.OrderResponse;
import acm.javaspring.shared.dto.order.OrderSummaryResponse;
import acm.javaspring.shared.dto.payment.PaymentResponse;
import acm.javaspring.shared.enums.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private final InventoryClient inventoryClient;
    private final CustomerValidationClient customerValidationClient;
    private final PaymentClient paymentClient;
    private final OrderStateStore orderStateStore;
    private final Executor orderTaskExecutor;

    public OrderProcessingServiceImpl(
            InventoryClient inventoryClient,
            CustomerValidationClient customerValidationClient,
            PaymentClient paymentClient,
            OrderStateStore orderStateStore,
            Executor orderTaskExecutor
    ) {
        this.inventoryClient = inventoryClient;
        this.customerValidationClient = customerValidationClient;
        this.paymentClient = paymentClient;
        this.orderStateStore = orderStateStore;
        this.orderTaskExecutor = orderTaskExecutor;
    }

    @Override
    public OrderResponse processOrder(OrderRequest request) {
        orderStateStore.create(request);
        orderStateStore.update(request.orderId(), OrderStatus.VALIDATING, "Order validation started");

        CompletableFuture<CustomerValidationResponse> customerFuture =
                CompletableFuture.supplyAsync(
                        () -> customerValidationClient.validate(request),
                        orderTaskExecutor
                );

        CompletableFuture<InventoryResponse> inventoryFuture =
                CompletableFuture.supplyAsync(
                        () -> inventoryClient.check(request),
                        orderTaskExecutor
                );

        CompletableFuture.allOf(customerFuture, inventoryFuture).join();

        CustomerValidationResponse customerResult = customerFuture.join();
        InventoryResponse inventoryResult = inventoryFuture.join();

        if (!customerResult.valid()) {
            orderStateStore.update(request.orderId(), OrderStatus.REJECTED, customerResult.message());
            return new OrderResponse(request.orderId(), OrderStatus.REJECTED, customerResult.message());
        }

        if (!inventoryResult.available()) {
            orderStateStore.update(request.orderId(), OrderStatus.REJECTED, inventoryResult.message());
            return new OrderResponse(request.orderId(), OrderStatus.REJECTED, inventoryResult.message());
        }

        orderStateStore.update(request.orderId(), OrderStatus.VALIDATED, "Customer and inventory validation successful");

        InventoryResponse reserveResult = inventoryClient.reserve(request);
        if (!reserveResult.available()) {
            orderStateStore.update(request.orderId(), OrderStatus.FAILED, reserveResult.message());
            return new OrderResponse(request.orderId(), OrderStatus.FAILED, reserveResult.message());
        }

        orderStateStore.update(request.orderId(), OrderStatus.INVENTORY_RESERVED, "Inventory reserved successfully");
        orderStateStore.update(request.orderId(), OrderStatus.PAYMENT_PENDING, "Payment authorization in progress");

        PaymentResponse paymentResponse = paymentClient.authorize(request);

        if (!paymentResponse.approved()) {
            inventoryClient.release(request);
            orderStateStore.update(request.orderId(), OrderStatus.REJECTED, paymentResponse.message());
            return new OrderResponse(request.orderId(), OrderStatus.REJECTED, paymentResponse.message());
        }

        orderStateStore.update(request.orderId(), OrderStatus.COMPLETED, "Order processed successfully");
        return new OrderResponse(request.orderId(), OrderStatus.COMPLETED, "Order completed successfully");
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        OrderRecord record = orderStateStore.get(orderId);
        if (record == null) {
            return new OrderResponse(orderId, OrderStatus.FAILED, "Order not found");
        }

        return new OrderResponse(orderId, record.getStatus(), record.getMessage());
    }

    @Override
    public List<OrderSummaryResponse> getAllOrders() {
        return orderStateStore.getAll().values().stream()
                .map(record -> new OrderSummaryResponse(
                        record.getOrderRequest().orderId(),
                        record.getOrderRequest().customerId(),
                        record.getOrderRequest().totalAmount(),
                        record.getStatus()
                ))
                .toList();
    }
}
