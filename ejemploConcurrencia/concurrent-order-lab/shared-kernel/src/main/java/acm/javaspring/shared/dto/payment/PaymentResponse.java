package acm.javaspring.shared.dto.payment;

public record PaymentResponse(
        boolean approved,
        String transactionId,
        String message
) {
}