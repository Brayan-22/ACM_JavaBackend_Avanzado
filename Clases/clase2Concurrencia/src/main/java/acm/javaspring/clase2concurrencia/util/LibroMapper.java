package acm.javaspring.clase2concurrencia.util;

import acm.javaspring.clase2concurrencia.models.Libro;
import acm.javaspring.clase2concurrencia.persitence.entity.LibroEntity;

import java.util.List;

public final class LibroMapper {
    private LibroMapper(){}

    public static LibroEntity toEntity(Libro libro){
        return LibroEntity.builder()
                .id(libro.getId())
                .nombre(libro.getNombre())
                .autor(libro.getAutor())
                .build();
    }

    public static Libro toModel(LibroEntity libroEntity){
        return Libro.builder()
                .id(libroEntity.getId())
                .nombre(libroEntity.getNombre())
                .autor(libroEntity.getAutor())
                .build();
    }

    public static List<Libro> toModelList(List<LibroEntity> libroEntities){
        return libroEntities.stream()
                .map(LibroMapper::toModel)
                .toList();
    }

    public static List<LibroEntity> toEntityList(List<Libro> libros){
        return libros.stream()
                .map(LibroMapper::toEntity)
                .toList();
    }
}
