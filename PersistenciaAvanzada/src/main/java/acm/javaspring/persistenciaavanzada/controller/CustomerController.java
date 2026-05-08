package acm.javaspring.persistenciaavanzada.controller;

import acm.javaspring.persistenciaavanzada.dto.CustomerResponseDTO;
import acm.javaspring.persistenciaavanzada.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerJpaRepository jpaRepository;

    @GetMapping
    public ResponseEntity<Object> getAll(){
        var all = jpaRepository.findAll();
        var response = all.isEmpty()? ResponseEntity.badRequest().build() : all.stream().map(
                customerEntity -> new CustomerResponseDTO(
                        customerEntity.getId(),
                        customerEntity.getFullName(),
                        customerEntity.getEmail()
                )).toList();
        return ResponseEntity.ok(response);
    }
}
