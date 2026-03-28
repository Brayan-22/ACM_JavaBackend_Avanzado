package acm.javaspring.persistenciaavanzada.service.impl;

import acm.javaspring.persistenciaavanzada.dto.CreateProductRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.ProductResponseDTO;
import acm.javaspring.persistenciaavanzada.exception.BusinessException;
import acm.javaspring.persistenciaavanzada.persistence.entity.ProductEntity;
import acm.javaspring.persistenciaavanzada.persistence.repository.ProductJpaRepository;
import acm.javaspring.persistenciaavanzada.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductJpaRepository productRepository;

    @Override
    @Transactional
    public ProductResponseDTO create(CreateProductRequestDTO request) {
        ProductEntity product = ProductEntity.builder()
                .sku(request.sku())
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .deleted(false)
                .build();
        ProductEntity saved = productRepository.save(product);
        return toResponse(saved);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAllActive() {
        return productRepository.findAllByDeletedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        ProductEntity product = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new BusinessException("Producto no encontrado"));

        product.softDelete();
    }

    private ProductResponseDTO toResponse(ProductEntity product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getVersion(),
                product.isDeleted()
        );
    }
}
