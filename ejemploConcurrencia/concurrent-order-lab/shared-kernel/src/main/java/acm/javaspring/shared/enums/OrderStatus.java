package acm.javaspring.shared.enums;

public enum OrderStatus {
    CREATED,
    VALIDATING,
    VALIDATED,
    INVENTORY_RESERVED,
    PAYMENT_PENDING,
    COMPLETED,
    REJECTED,
    FAILED
}