package com.architecture.modular.inventario;

import com.architecture.modular.shared.OrdenCreadaEvent;
import com.architecture.modular.shared.OrdenValidadaEvent;
import com.architecture.modular.shared.OrdenRechazadaEvent;
import com.architecture.modular.shared.port.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository repository;
    private final DomainEventPublisher eventPublisher;

    public List<Inventario> findAll() {
        return repository.findAll();
    }

    public Optional<Inventario> findByProductoId(Long productoId) {
        return repository.findByProductoId(productoId);
    }

    public Inventario save(Inventario inventario) {
        return repository.save(inventario);
    }

    public Optional<Inventario> updateStock(Long productoId, int stock) {
        return repository.findByProductoId(productoId)
                .map(existing -> {
                    existing.setStock(stock);
                    return repository.save(existing);
                });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Transactional
    public void procesarOrdenCreada(OrdenCreadaEvent event) {
        // Primera pasada: Validar TODO el inventario antes de descontar parcialmente
        for (OrdenCreadaEvent.OrdenItemPayload payload : event.items()) {
            var inventarioOpt = repository.findByProductoId(payload.productoId());
            
            if (inventarioOpt.isEmpty()) {
                eventPublisher.publish(new OrdenRechazadaEvent(event.ordenId(), "Inventario inexistente para producto " + payload.productoId()));
                return; // 🛑 Detener ejecución SAGA (Fallo en negocio)
            }
            
            if (inventarioOpt.get().getStock() < payload.cantidad()) {
                eventPublisher.publish(new OrdenRechazadaEvent(event.ordenId(), "Stock insuficiente para producto " + payload.productoId()));
                return; // 🛑 Detener ejecución SAGA (Fallo en negocio)
            }
        }

        // Segunda pasada: Todo está validado, hacemos la mutación (disminuir el stock)
        for (OrdenCreadaEvent.OrdenItemPayload payload : event.items()) {
            var inventario = repository.findByProductoId(payload.productoId()).get();
            inventario.setStock(inventario.getStock() - payload.cantidad());
            repository.save(inventario);
        }

        // Emitimos el evento exitoso
        eventPublisher.publish(new OrdenValidadaEvent(event.ordenId()));
    }
}
