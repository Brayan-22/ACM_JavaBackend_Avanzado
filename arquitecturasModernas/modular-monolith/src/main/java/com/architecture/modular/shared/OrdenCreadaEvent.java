package com.architecture.modular.shared;

import java.util.List;

public record OrdenCreadaEvent(Long ordenId, List<OrdenItemPayload> items) {
    public record OrdenItemPayload(Long productoId, int cantidad) {}
}
