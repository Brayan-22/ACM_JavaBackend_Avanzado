package acm.javaspring.customer.controller;

import acm.javaspring.customer.service.CustomerValidationService;
import acm.javaspring.shared.dto.customer.CustomerValidationRequest;
import acm.javaspring.shared.dto.customer.CustomerValidationResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerValidationController {

    private final CustomerValidationService customerValidationService;

    public CustomerValidationController(CustomerValidationService customerValidationService) {
        this.customerValidationService = customerValidationService;
    }

    @PostMapping("/validate")
    public ResponseEntity<CustomerValidationResponse> validateCustomer(
            @Valid @RequestBody CustomerValidationRequest request
    ) {
        CustomerValidationResponse response = customerValidationService.validateCustomer(request);

        if (response.valid()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(response);
    }
}