package acm.javaspring.persistenciaavanzada.service;

import acm.javaspring.persistenciaavanzada.dto.CreateProductRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(CreateProductRequestDTO request);
    List<ProductResponseDTO> findAllActive();
    void softDelete(Long id);
}
