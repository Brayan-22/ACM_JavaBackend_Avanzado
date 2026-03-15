package acm.javaspring.order.client;

import acm.javaspring.shared.dto.order.OrderRequest;
import acm.javaspring.shared.dto.payment.PaymentRequest;
import acm.javaspring.shared.dto.payment.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PaymentClient {
    private final RestClient restClient;

    public PaymentClient(@Value("${services.payment.base-url}") String paymentBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(paymentBaseUrl)
                .build();
    }

    public PaymentResponse authorize(OrderRequest orderRequest) {
        PaymentRequest request = new PaymentRequest(
                orderRequest.orderId(),
                orderRequest.customerId(),
                orderRequest.totalAmount()
        );

        return restClient.post()
                .uri("/api/payments/authorize")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(PaymentResponse.class);
    }
}
