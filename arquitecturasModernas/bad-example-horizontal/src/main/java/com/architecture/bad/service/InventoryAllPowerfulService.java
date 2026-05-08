package com.architecture.bad.service;

import com.architecture.bad.entity.Inventory;
import com.architecture.bad.entity.Product;
import com.architecture.bad.repository.InventoryRepository;
import com.architecture.bad.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Anti-patron: Servicio todo poderoso.
 * Falla SRP: Hace reportes, notificaciones falsas, lógicas de stock, y
 * modificaciones de productos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryAllPowerfulService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void restockAndActivateProductAndSendNotification(Long productId, int amountToRestock) {
        // Hace de todo
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        if (!product.isActive()) {
            product.setActive(true);
            productRepository.save(product);
        }

        Inventory inv = inventoryRepository.findByProductId(productId);
        if (inv != null) {
            inv.setQuantity(inv.getQuantity() + amountToRestock);
            inventoryRepository.save(inv);
        } else {
            inv = new Inventory();
            inv.setProduct(product);
            inv.setQuantity(amountToRestock);
            inventoryRepository.save(inv);
        }

        // Simula el envio de un correo
        log.info("Email sent to admin: Product {} restocked!", product.getName());
    }
}
