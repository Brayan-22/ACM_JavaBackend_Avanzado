package acm.javaspring.persistenciaavanzada.controller;

import acm.javaspring.persistenciaavanzada.dto.CreateProductRequestDTO;
import acm.javaspring.persistenciaavanzada.dto.ProductResponseDTO;
import acm.javaspring.persistenciaavanzada.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO create(@RequestBody @Valid CreateProductRequestDTO request) {
        return productService.create(request);
    }

    @GetMapping
    public List<ProductResponseDTO> list() {
        return productService.findAllActive();
    }

    @DeleteMapping("/{id}")
    public void softDelete(@PathVariable Long id) {
        productService.softDelete(id);
    }
}