package acm.javaspring.clase2concurrencia.service;

import acm.javaspring.clase2concurrencia.configuration.AsyncConfiguration;
import acm.javaspring.clase2concurrencia.models.Libro;
import acm.javaspring.clase2concurrencia.persitence.repository.LibroRepository;
import acm.javaspring.clase2concurrencia.util.LibroMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService{

    private final LibroRepository libroRepository;
    private final AsyncConfiguration asyncConfiguration;

    @Override
    @Transactional
    public void crearLibro(Libro libro) {
        var entity = LibroMapper.toEntity(libro);
        asyncConfiguration.enviarCorreoAsync();
        libroRepository.save(entity);
    }

    @Override
    public void eliminarLibro(Integer id) {
        libroRepository.deleteById(id);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void actualizarLibro(Integer id, Libro libro) {
        if(libroRepository.findById(id).isEmpty()){
            throw new RuntimeException("Libro no encontrado");
        }
        var entity = LibroMapper.toEntity(libro);
        entity.setId(id);
        libroRepository.save(entity);
    }



    @Override
    public Libro obtenerLibro(Integer id) {
        return libroRepository.findById(id)
                .map(LibroMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll()
                .stream()
                .map(LibroMapper::toModel)
                .toList();
    }
}
