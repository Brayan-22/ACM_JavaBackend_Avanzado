package acm.javaspring.models;

import java.util.List;

public record ReporteGenero(
        GeneroEnum genero,
        Long cantidadLibros,
        List<String> titulos,
        Integer anioMasAntiguo,
        Integer anioMasReciente
) {
}
