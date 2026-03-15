package acm.javaspring.customer.service.impl;

import acm.javaspring.customer.service.CustomerValidationService;
import acm.javaspring.shared.dto.customer.CustomerValidationRequest;
import acm.javaspring.shared.dto.customer.CustomerValidationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class CustomerValidationServiceImpl implements CustomerValidationService {
    private static final Set<String> BLACKLISTED_CUSTOMERS = Set.of(
            "BLACKLIST-001",
            "BLACKLIST-002",
            "FRAUD-999"
    );

    private static final Set<String> INACTIVE_CUSTOMERS = Set.of(
            "INACTIVE-100",
            "INACTIVE-200"
    );

    private static final BigDecimal MAX_ALLOWED_AMOUNT = new BigDecimal("5000.00");

    @Override
    public CustomerValidationResponse validateCustomer(CustomerValidationRequest request) {
        String customerId = request.customerId();
        BigDecimal amount = request.amount();

        simulateLatency();

        if (BLACKLISTED_CUSTOMERS.contains(customerId)) {
            return new CustomerValidationResponse(
                    false,
                    "Customer is blacklisted: " + customerId
            );
        }

        if (INACTIVE_CUSTOMERS.contains(customerId)) {
            return new CustomerValidationResponse(
                    false,
                    "Customer is inactive: " + customerId
            );
        }

        if (amount.compareTo(MAX_ALLOWED_AMOUNT) > 0) {
            return new CustomerValidationResponse(
                    false,
                    "Amount exceeds allowed limit for customer validation"
            );
        }

        return new CustomerValidationResponse(
                true,
                "Customer validation successful"
        );
    }

    private void simulateLatency() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
