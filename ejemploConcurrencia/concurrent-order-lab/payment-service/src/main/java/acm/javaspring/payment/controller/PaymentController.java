package acm.javaspring.payment.controller;

import acm.javaspring.payment.service.PaymentService;
import acm.javaspring.shared.dto.payment.PaymentRequest;
import acm.javaspring.shared.dto.payment.PaymentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorize")
    public ResponseEntity<PaymentResponse> authorizePayment(
            @Valid @RequestBody PaymentRequest request
    ) {
        PaymentResponse response = paymentService.authorizePayment(request);

        if (response.approved()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}
