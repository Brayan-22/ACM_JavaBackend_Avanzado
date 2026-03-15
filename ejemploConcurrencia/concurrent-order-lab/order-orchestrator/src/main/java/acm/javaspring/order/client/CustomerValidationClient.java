package acm.javaspring.order.client;

import acm.javaspring.shared.dto.customer.CustomerValidationRequest;
import acm.javaspring.shared.dto.customer.CustomerValidationResponse;
import acm.javaspring.shared.dto.order.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomerValidationClient {

    private final RestClient restClient;

    public CustomerValidationClient(
            @Value("${services.customer-validation.base-url}") String customerValidationBaseUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(customerValidationBaseUrl)
                .build();
    }

    public CustomerValidationResponse validate(OrderRequest orderRequest) {
        CustomerValidationRequest request = new CustomerValidationRequest(
                orderRequest.customerId(),
                orderRequest.totalAmount()
        );

        return restClient.post()
                .uri("/api/customers/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(CustomerValidationResponse.class);
    }
}
