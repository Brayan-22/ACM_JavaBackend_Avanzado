package acm.javaspring.clase2concurrencia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsuariosService {
    private final RestClient restClient;


    public Integer obtenerResumenUsuarios(){
        try{
//            var response = restClient.get()
//                    .uri("http://localhost:8082/api/v1/usuarios/resumen")
//                    .retrieve();
//            return response != null ? Integer.valueOf(response.toString()) : 0;
            Thread.sleep(new Random().nextInt(5));
            return new Random().nextInt(100);
        }catch (Exception ex){
            throw new RuntimeException("Error al obtener resumen de usuarios: " + ex.getMessage());
        }
    }
}
