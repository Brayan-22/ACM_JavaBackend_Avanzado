package acm.javaspring.payment.service;

import acm.javaspring.shared.dto.payment.PaymentRequest;
import acm.javaspring.shared.dto.payment.PaymentResponse;

public interface PaymentService {
    PaymentResponse authorizePayment(PaymentRequest request);
}
