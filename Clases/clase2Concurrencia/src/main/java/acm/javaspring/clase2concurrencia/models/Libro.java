package acm.javaspring.clase2concurrencia.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Libro {
    private Integer id;
    private String nombre;
    private String autor;
}
