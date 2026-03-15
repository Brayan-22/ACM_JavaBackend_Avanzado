package acm.javaspring.payment.service.impl;

import acm.javaspring.payment.service.PaymentService;
import acm.javaspring.shared.dto.payment.PaymentRequest;
import acm.javaspring.shared.dto.payment.PaymentResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal MAX_APPROVED_AMOUNT = new BigDecimal("3000.00");

    @Override
    public PaymentResponse authorizePayment(PaymentRequest request) {
        simulateLatency();

        if (request.amount().compareTo(MAX_APPROVED_AMOUNT) > 0) {
            return new PaymentResponse(
                    false,
                    null,
                    "Payment rejected: amount exceeds authorization limit"
            );
        }

        if (request.customerId().startsWith("RISK-")) {
            return new PaymentResponse(
                    false,
                    null,
                    "Payment rejected: high-risk customer"
            );
        }

        return new PaymentResponse(
                true,
                UUID.randomUUID().toString(),
                "Payment approved"
        );
    }

    private void simulateLatency() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
