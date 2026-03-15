package acm.javaspring.clase2concurrencia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PedidosService {
    private final RestClient restClient;

    public Integer obtenerResumenPedidos(){
        try{
//            var response = restClient.get()
//                    .uri("http://localhost:8081/api/v1/pedidos/resumen")
//                    .retrieve();
            Thread.sleep(5);
            return new Random().nextInt(5);
        }catch (Exception ex){
            throw new RuntimeException("Error al obtener resumen de pedidos: " + ex.getMessage());
        }
    }
}
