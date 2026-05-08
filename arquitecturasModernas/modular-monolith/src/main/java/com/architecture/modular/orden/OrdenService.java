package com.architecture.modular.orden;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.architecture.modular.shared.OrdenCreadaEvent;
import com.architecture.modular.shared.port.DomainEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenService {
    private final OrdenRepository repository;
    private final DomainEventPublisher eventPublisher;

    public List<Orden> findAll() {
        return repository.findAll();
    }

    public Optional<Orden> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Orden save(Orden orden) {
        orden.setEstado("PENDIENTE_INVENTARIO");
        Orden ordenGuardada = repository.save(orden);

        // Disparar evento para que otros módulos reaccionen (ej. Inventario)
        List<OrdenCreadaEvent.OrdenItemPayload> payloadItems = ordenGuardada.getItems().stream()
                .map(item -> new OrdenCreadaEvent.OrdenItemPayload(item.getProductoId(), item.getCantidad()))
                .collect(Collectors.toList());

        eventPublisher.publish(new OrdenCreadaEvent(ordenGuardada.getId(), payloadItems));

        return ordenGuardada;
    }

    @Transactional
    public void marcarComoValidada(Long ordenId) {
        repository.findById(ordenId).ifPresent(orden -> {
            orden.setEstado("VALIDADA_CON_STOCK");
            repository.save(orden);
        });
    }

    @Transactional
    public void marcarComoRechazada(Long ordenId, String motivo) {
        repository.findById(ordenId).ifPresent(orden -> {
            orden.setEstado("RECHAZADA_SIN_STOCK");
            // Aquí podríamos guardar el 'motivo' en un campo de la base de datos si la Entidad Orden tuviera un atributo 'notas' o 'motivoRechazo'.
            repository.save(orden);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
