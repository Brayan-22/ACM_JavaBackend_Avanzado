package acm.javaspring.persistenciaavanzada.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String sku,
        String name,
        BigDecimal price,
        Integer stock,
        Long version,
        boolean deleted
) {
}