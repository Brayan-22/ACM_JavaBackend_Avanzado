package com.acm.inventoryservices.service.impl;

import com.acm.inventoryservices.persistence.entity.InventoryReservation;
import com.acm.inventoryservices.persistence.entity.ProductStock;
import com.acm.inventoryservices.persistence.entity.ReservationId;
import com.acm.inventoryservices.persistence.repository.InventoryReservationJpaRepository;
import com.acm.inventoryservices.persistence.repository.ProductStockJpaRepository;
import com.acm.inventoryservices.service.InventoryService;
import com.acm.inventoryservices.shared.OrderItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final ProductStockJpaRepository stockJpaRepository;
    private final InventoryReservationJpaRepository inventoryReservationJpaRepository;

    @Transactional
    @Override
    public boolean reserveStock(String orderId, List<OrderItem> items) {
        // Validar disponibilidad
        for (OrderItem item : items){
            Optional<ProductStock> optionalProductStock = stockJpaRepository.findById(item.getProductId());
            if (optionalProductStock.isEmpty() || optionalProductStock.get().getAvailableQuantity() < item.getQuantity()){
                log.warn("Stock insuficiente para producto: {} y pedido : {}", item.getProductId(), orderId);
                return false;
            }
        }
        for (OrderItem item: items){
            ProductStock stock = stockJpaRepository.findById(item.getProductId()).orElseThrow();
            stock.decreaseQuantity(item.getQuantity());
            stockJpaRepository.save(stock);

            // Crear registro de reserva
            ReservationId reservationId = new ReservationId(orderId, item.getProductId());
            InventoryReservation reservation = new InventoryReservation(reservationId, item.getQuantity());
            inventoryReservationJpaRepository.save(reservation);
        }
        log.info("Stock reservado exitosamente para pedido: {}", orderId);
        return true;
    }

    @Transactional
    @Override
    public void releaseStock(String orderId) {
        List<InventoryReservation> reservations = inventoryReservationJpaRepository.findByIdOrderId(orderId);
        if (reservations.isEmpty()) {
            log.info("No hay reservas para liberar para pedido {}", orderId);
            return;
        }

        for (InventoryReservation res : reservations) {
            ProductStock stock = stockJpaRepository.findById(res.getId().getProductId()).orElseThrow();
            stock.increaseQuantity(res.getQuantity());
            stockJpaRepository.save(stock);
            inventoryReservationJpaRepository.delete(res);
        }
        log.info("Stock liberado para pedido {}", orderId);
    }
}
