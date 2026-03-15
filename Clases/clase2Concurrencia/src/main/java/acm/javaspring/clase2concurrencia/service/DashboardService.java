package acm.javaspring.clase2concurrencia.service;

import acm.javaspring.clase2concurrencia.configuration.AsyncConfiguration;
import acm.javaspring.clase2concurrencia.models.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final PedidosService pedidosService;
    private final AsyncConfiguration asyncConfiguration;

    public DashboardDTO getDashboard(){
        var future1 = asyncConfiguration.obtenerResumenUsuariosAsync();
        var future2 = asyncConfiguration.obtenerResumenPedidosAsync();
        var future3 = asyncConfiguration.enviarCorreoAsync();
        future3.join();
        return DashboardDTO.builder()
                .resumenClientes(future1.join())
                .resumenPedidos(future2.join())
                .build();
    }
}
