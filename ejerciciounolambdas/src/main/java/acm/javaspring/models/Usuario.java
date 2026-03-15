package acm.javaspring.models;

public record Usuario(
        String id, String nombre, String email, NivelMembresiaEnum nivel
) {
}
