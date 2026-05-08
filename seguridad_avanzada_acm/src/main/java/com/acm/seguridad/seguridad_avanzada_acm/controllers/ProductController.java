package com.acm.seguridad.seguridad_avanzada_acm.controllers;

import com.acm.seguridad.seguridad_avanzada_acm.models.Product;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // RBAC: Solo usuarios con Rol ADMIN pueden crear
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@Valid @RequestBody Product product,
            org.springframework.security.core.Authentication auth) {
        String username = auth.getName();
        if (auth.getPrincipal() instanceof org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser) {
            username = ((org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser) auth.getPrincipal())
                    .getPreferredUsername();
        }
        product.setOwnerUsername(username);
        return productRepository.save(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ABAC Híbrido: Puedes borrar si eres el Dueño (ABAC) o si eres Administrador
    // General (RBAC)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @abacEvaluator.isOwner(authentication, #id)")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    // ABAC Puro: No importan los Roles. Solo importa el DEPARTAMENTO del usuario
    // conectado.
    @GetMapping("/audit")
    @PreAuthorize("@abacEvaluator.isInDepartment(authentication, 'IT')")
    public ResponseEntity<?> getProductAudits() {
        return ResponseEntity.ok(List.of("Auditoria 1: Sistema OK", "Auditoria 2: Sin brechas de seguridad"));
    }
}
