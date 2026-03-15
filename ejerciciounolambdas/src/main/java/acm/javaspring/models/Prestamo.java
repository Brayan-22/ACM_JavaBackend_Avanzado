package acm.javaspring.models;

import java.time.LocalDate;

public record Prestamo(
        String libroIsbn, String usuarioId, LocalDate fechaInicio, LocalDate fechaDevolucion, Boolean devuelto
) {
}
