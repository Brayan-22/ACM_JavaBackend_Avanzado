package acm.javaspring.persistenciaavanzada.service.impl;

import acm.javaspring.persistenciaavanzada.dto.CreateOrderItemRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.CreateOrderRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.OrderResponseDTO;
import acm.javaspring.persistenciaavanzada.exception.BusinessException;
import acm.javaspring.persistenciaavanzada.persistence.entity.*;
import acm.javaspring.persistenciaavanzada.persistence.repository.CustomerJpaRepository;
import acm.javaspring.persistenciaavanzada.persistence.repository.OrderJpaRepository;
import acm.javaspring.persistenciaavanzada.persistence.repository.ProductJpaRepository;
import acm.javaspring.persistenciaavanzada.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerJpaRepository customerJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final OrderJpaRepository orderJpaRepository;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request) {
        CustomerEntity customer = customerJpaRepository.findById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cliente no encontrado"));

        PurchaseOrderEntity order = PurchaseOrderEntity.builder()
                .customer(customer)
                .status(OrderStatus.CREATED)
                .total(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        List<OrderResponseDTO.OrderItemDetail> responseItems = new ArrayList<>();

        for (CreateOrderItemRequestDTO itemRequest : request.items()) {
            ProductEntity product = productJpaRepository.findByIdForUpdate(itemRequest.productId())
                    .orElseThrow(() -> new BusinessException("Producto no encontrado: " + itemRequest.productId()));

            product.decreaseStock(itemRequest.quantity());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));

            OrderItemEntity item = OrderItemEntity.builder()
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            order.addItem(item);
            total = total.add(subtotal);

            responseItems.add(new OrderResponseDTO.OrderItemDetail(
                    product.getId(),
                    product.getName(),
                    itemRequest.quantity(),
                    product.getPrice(),
                    subtotal
            ));
        }

        order.setTotal(total);
        PurchaseOrderEntity saved = orderJpaRepository.save(order);

        return new OrderResponseDTO(
                saved.getId(),
                customer.getId(),
                saved.getStatus().name(),
                saved.getTotal(),
                responseItems
        );
    }
}
