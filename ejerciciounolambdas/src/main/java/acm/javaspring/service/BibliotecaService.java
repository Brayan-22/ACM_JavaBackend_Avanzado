package acm.javaspring.service;


import acm.javaspring.exceptions.UsuarioNoEncontradoException;
import acm.javaspring.models.*;
import acm.javaspring.utils.JsonReaderUtil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;



public class BibliotecaService {
    private final List<Libro> libros = JsonReaderUtil.getLibros();
    private final List<Usuario> usuarios = JsonReaderUtil.getUsuarios();
    private final List<Prestamo> prestamos = JsonReaderUtil.getPrestamos();

    /*
    1. Libro más reciente por autor
    Dado un autor, retorna el Optional<Libro> con el año más alto. Usar filter + max.
    */
    public Optional<Libro> mostRecentBookByAutor(String autor){
        return libros
                .stream().filter(libro -> libro.autor().equalsIgnoreCase(autor))
                .max(Comparator.comparingInt(Libro::anio));
    }
    /**
     * 2. Libros por género con filtro de rango de años
     * Retorna un Map<Genero, List<Libro>> con los libros ordenados por año, filtrando solo los publicados entre anioDesde y anioHasta.
     */
    public Map<GeneroEnum, List<Libro>> orderedBooksByYearFilterByRange(Integer anioInicio, Integer anioFin){
        return libros
                .stream()
                .filter(libro -> libro.anio() >= anioInicio && libro.anio() <= anioFin)
                .sorted(Comparator.comparingInt(Libro::anio))
                .collect(Collectors.groupingBy(Libro::genero));
    }

    /*
    3. Estadísticas por usuario
    Retorna un Map<String, EstadisticaUsuario> que incluya: total de préstamos, préstamos activos, préstamos vencidos, promedio de días (solo devueltos) e ISBNs distintos prestados.
    El límite de días para considerar un préstamo vencido debe variar según la membresía: 14 días para BASICO, 30 para PREMIUM, 60 para INVESTIGADOR.
    */
    public Map<String, EstadisticaUsuario> userStatistics(){
        LocalDate today = LocalDate.now();
        Map<String, Usuario> usuariosPorId = usuarios
                .stream().collect(Collectors.toMap(Usuario::id, usuario -> usuario));
        return prestamos
                .stream()
                .collect(Collectors.groupingBy(Prestamo::usuarioId))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        stringListEntry -> stringListEntry.getKey(),
                        listEntry -> {
                            String usuarioId = listEntry.getKey();
                            List<Prestamo> prestamosUsuario = listEntry.getValue();
                            Usuario usuario = usuariosPorId.get(usuarioId);
                            if (usuario == null){
                                throw new UsuarioNoEncontradoException(usuarioId);
                            }
                            long totalPrestamos = prestamosUsuario.size();
                            long prestamosActivos = prestamosUsuario.stream().filter(p -> !p.devuelto()).count();
                            long prestamosVencidos = prestamosUsuario.stream().filter(p -> !p.devuelto())
                                    .filter(p -> today.isAfter(p.fechaInicio().plusDays(NivelMembresiaEnum.diasPrestamoSegunMembresia(usuario.nivel()))))
                                    .count();
                            OptionalDouble promedioDias = prestamosUsuario.stream()
                                    .filter(Prestamo::devuelto)
                                    .mapToLong(p -> ChronoUnit.DAYS.between(p.fechaInicio(),p.fechaDevolucion()))
                                    .average();

                            List<String> isbsPrestados = prestamosUsuario.stream()
                                    .map(Prestamo::libroIsbn)
                                    .distinct()
                                    .sorted()
                                    .toList();
                            return new EstadisticaUsuario(
                                    usuarioId, totalPrestamos, prestamosActivos, prestamosVencidos, promedioDias.isPresent() ? promedioDias.getAsDouble():0d, isbsPrestados
                            );
                        }
                ));
    }

    /**
     * 4. Préstamos vencidos con detalle
     * Retorna una List<PrestamoVencido> (record anidado con usuarioId, libroIsbn y diasVencido), ordenada de mayor a menor antigüedad.
     */
    public record PrestamoVencido(
        String usuarioId,
        String libroIsbn,
        long diasVencido
    ){}
    public List<PrestamoVencido> overdueBooks(){
        LocalDate now = LocalDate.now();
        return prestamos
                .stream()
                .filter(p -> !p.devuelto())
                .filter(p -> now.isAfter(p.fechaInicio().plusDays(
                        NivelMembresiaEnum.diasPrestamoSegunMembresia(
                                usuarios
                                        .stream()
                                        .filter(u -> u.id().equals(p.usuarioId())).findFirst().orElseThrow(() -> new UsuarioNoEncontradoException(p.usuarioId()))
                                        .nivel()
                        )
                )))
                .map(p -> new PrestamoVencido(
                        p.usuarioId(),
                        p.libroIsbn(),
                        ChronoUnit.DAYS.between(p.fechaInicio().plusDays(
                                NivelMembresiaEnum.diasPrestamoSegunMembresia(
                                        usuarios.stream().filter(u -> u.id().equals(p.usuarioId())).findFirst().orElseThrow(() -> new UsuarioNoEncontradoException(p.usuarioId())).nivel()
                                )
                        ), now)
                ))
                .sorted(Comparator.comparingLong(PrestamoVencido::diasVencido).reversed())
                .toList();
    }
    /**
     * 5. Reporte enriquecido por género
     * Retorna un Map<Genero, ReporteGenero>
     *     donde cada entrada incluye: cantidad de libros, lista de títulos ordenada por año descendente, año más antiguo y año más reciente del género.
     */
    public record ResporteGenero(
        Long cantidadLibros,
        List<String> titulosOrdenadosPorAnioDesc,
        Integer anioMasAntiguo,
        Integer anioMasReciente){}
    public Map<GeneroEnum, ResporteGenero> enrichedGenreReport(){
        return libros
                .stream()
                .collect(Collectors.groupingBy(Libro::genero))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        generoEntry -> generoEntry.getKey(),
                        librosList -> {
                            var librosDelGenero = librosList.getValue();
                            Long cantidadLibrosPorGenero = (long) librosDelGenero.size();
                            List<String> titulosOrdenadosPorAnioDesc = librosDelGenero.stream()
                                    .sorted(Comparator.comparingInt(Libro::anio).reversed())
                                    .map(Libro::titulo)
                                    .toList();
                            int anioMasAntiguo = librosDelGenero.stream().mapToInt(Libro::anio).min().orElse(0);
                            int anioMasReciente = librosDelGenero.stream().mapToInt(Libro::anio).max().orElse(0);
                            return new ResporteGenero(cantidadLibrosPorGenero, titulosOrdenadosPorAnioDesc, anioMasAntiguo, anioMasReciente);
                        }
                ));
    }

    /**
     * 6. Disponibilidad de copias en tiempo real
     * Retorna un Map<String, Integer> de ISBN → copias disponibles, calculado como copiasTotales - préstamos activos. Usa groupingBy + counting.
     */
    public Map<String, Integer> copyDisponibility() {
        Map<String, Long> prestamosActivosPorLibro = prestamos
                .stream()
                .filter(p -> !p.devuelto())
                .collect(Collectors.groupingBy(Prestamo::libroIsbn, Collectors.counting()));

        return libros.stream()
                .collect(Collectors.toMap(
                        Libro::isbn,
                        libro -> libro.copiasTotales() - Math.toIntExact(
                                prestamosActivosPorLibro.getOrDefault(libro.isbn(), 0L)
                        )
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, _) -> e1,
                        LinkedHashMap::new
                ));
    }
    /**
     * 7. Top N autores más prestados
     * Dado un número N, retorna los N autores con más préstamos históricos (incluyendo devueltos).
     * Requiere hacer un join entre la lista de libros y la de préstamos mediante un Map intermedio.
     */
    public List<String> autoresMasPrestados(Integer n){
        Map<String, String> isbnToAutor = libros.stream()
                .collect(Collectors.toMap(Libro::isbn, Libro::autor));

        Map<String, Long> prestamosPorAutor = prestamos.stream()
                .map(Prestamo::libroIsbn)
                .filter(isbnToAutor::containsKey)
                .map(isbnToAutor::get)
                .collect(Collectors.groupingBy(autor -> autor, Collectors.counting()));

        return prestamosPorAutor.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }
}
