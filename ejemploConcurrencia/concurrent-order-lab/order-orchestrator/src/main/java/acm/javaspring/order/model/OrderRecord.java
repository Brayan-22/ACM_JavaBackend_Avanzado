package acm.javaspring.order.model;


import acm.javaspring.shared.dto.order.OrderRequest;
import acm.javaspring.shared.enums.OrderStatus;

public class OrderRecord {

    private final OrderRequest orderRequest;
    private volatile OrderStatus status;
    private volatile String message;

    public OrderRecord(OrderRequest orderRequest, OrderStatus status, String message) {
        this.orderRequest = orderRequest;
        this.status = status;
        this.message = message;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public synchronized void updateStatus(OrderStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
