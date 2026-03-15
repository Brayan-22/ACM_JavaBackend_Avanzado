package acm.javaspring.utils;

import acm.javaspring.models.Libro;
import acm.javaspring.models.Prestamo;
import acm.javaspring.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class JsonReaderUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private JsonReaderUtil() {
    }

    public record BibliotecaData(
            List<Libro> libros,
            List<Usuario> usuarios,
            List<Prestamo> prestamos
    ) {
    }

    private static final BibliotecaData BIBLIOTECA_DATA = cargarBibliotecaData();

    private static BibliotecaData cargarBibliotecaData() {
        InputStream resourceStream = JsonReaderUtil.class.getResourceAsStream("/data.json");
        if (resourceStream == null) {
            throw new IllegalStateException("No se encontró el archivo /data.json en resources");
        }

        try {
            return OBJECT_MAPPER.readValue(resourceStream, BibliotecaData.class);
        } catch (IOException e) {
            throw new IllegalStateException("Error al cargar los datos de biblioteca", e);
        }
    }

    public static List<Libro> getLibros() {
        return BIBLIOTECA_DATA.libros();
    }

    public static List<Usuario> getUsuarios() {
        return BIBLIOTECA_DATA.usuarios();
    }

    public static List<Prestamo> getPrestamos() {
        return BIBLIOTECA_DATA.prestamos();
    }
}
