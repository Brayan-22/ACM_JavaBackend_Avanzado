package acm.javaspring.shared.dto.customer;

public record CustomerValidationResponse(
        boolean valid,
        String message
) {
}