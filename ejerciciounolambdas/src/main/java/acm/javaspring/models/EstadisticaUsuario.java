package acm.javaspring.models;

import java.util.List;
import java.util.OptionalDouble;

public record EstadisticaUsuario(
        String usuarioId,
        Long totalPrestamos,
        Long prestamosActivos,
        Long prestamosVencidos,
        Double promedioDias,
        List<String> isbnsPrestados
) {
}
