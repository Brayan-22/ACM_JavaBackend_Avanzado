package acm.javaspring.clase2concurrencia.controller;

import acm.javaspring.clase2concurrencia.models.Libro;
import acm.javaspring.clase2concurrencia.service.DashboardService;
import acm.javaspring.clase2concurrencia.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/libro/")
@Valid
@RequiredArgsConstructor
public class LibraryController {
    private final LibroService libroService;
    private final DashboardService dashboardService;
    @PostMapping
    public ResponseEntity<Object> createLibro(@RequestBody Libro libro){
        libroService.crearLibro(libro);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(libroService.obtenerTodosLosLibros());
    }

    @GetMapping("/metrics")
    public ResponseEntity<Object> getMetrics(){
        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}
