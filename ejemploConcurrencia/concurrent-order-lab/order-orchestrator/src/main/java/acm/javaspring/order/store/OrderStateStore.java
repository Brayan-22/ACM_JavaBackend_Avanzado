package acm.javaspring.order.store;

import acm.javaspring.order.model.OrderRecord;
import acm.javaspring.shared.dto.order.OrderRequest;
import acm.javaspring.shared.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderStateStore {
    private final ConcurrentHashMap<String, OrderRecord> orders = new ConcurrentHashMap<>();

    public void create(OrderRequest request) {
        orders.put(
                request.orderId(),
                new OrderRecord(request, OrderStatus.CREATED, "Order created")
        );
    }

    public OrderRecord get(String orderId) {
        return orders.get(orderId);
    }

    public void update(String orderId, OrderStatus status, String message) {
        OrderRecord record = orders.get(orderId);
        if (record != null) {
            record.updateStatus(status, message);
        }
    }

    public Map<String, OrderRecord> getAll() {
        return orders;
    }
}
