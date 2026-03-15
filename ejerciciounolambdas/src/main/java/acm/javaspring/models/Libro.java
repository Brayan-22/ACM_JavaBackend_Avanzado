package acm.javaspring.models;

public record Libro(
        String isbn, String titulo, String autor, Integer anio, GeneroEnum genero, Integer copiasTotales
) {
}
