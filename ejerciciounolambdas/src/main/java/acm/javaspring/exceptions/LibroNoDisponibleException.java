package acm.javaspring.exceptions;

public class LibroNoDisponibleException extends RuntimeException {
    public LibroNoDisponibleException(String isbn) {
        super("No hay copias disponibles del libro: "+ isbn);
    }
}
