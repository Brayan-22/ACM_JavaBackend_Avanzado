package acm.javaspring.clase2concurrencia.service;


import acm.javaspring.clase2concurrencia.models.Libro;

import java.util.List;

public interface LibroService {
        void crearLibro(Libro libro);
        void eliminarLibro(Integer id);
        void actualizarLibro(Integer id, Libro libro);
        Libro obtenerLibro(Integer id);
        List<Libro> obtenerTodosLosLibros();
}
