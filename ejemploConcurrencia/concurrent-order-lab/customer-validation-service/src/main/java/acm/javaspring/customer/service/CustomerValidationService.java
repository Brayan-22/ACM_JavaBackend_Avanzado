package acm.javaspring.customer.service;

import acm.javaspring.shared.dto.customer.CustomerValidationRequest;
import acm.javaspring.shared.dto.customer.CustomerValidationResponse;

public interface CustomerValidationService {
    CustomerValidationResponse validateCustomer(CustomerValidationRequest request);
}
