package acm.javaspring.persistenciaavanzada.service;

import acm.javaspring.persistenciaavanzada.dto.CreateOrderRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(CreateOrderRequestDTO request);
}
