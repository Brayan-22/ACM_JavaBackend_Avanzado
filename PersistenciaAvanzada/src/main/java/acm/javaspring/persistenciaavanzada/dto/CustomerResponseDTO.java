package acm.javaspring.persistenciaavanzada.dto;

import lombok.Builder;


public record CustomerResponseDTO(
        Long id,
        String fullName,
        String email
) {
}
