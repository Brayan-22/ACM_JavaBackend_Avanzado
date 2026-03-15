package acm.javaspring.clase2concurrencia.configuration;


import acm.javaspring.clase2concurrencia.service.EnvioCorreoService;
import acm.javaspring.clase2concurrencia.service.PedidosService;
import acm.javaspring.clase2concurrencia.service.UsuariosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncConfiguration {
    private final EnvioCorreoService envioCorreoService;
    private final UsuariosService usuariosService;
    private final PedidosService pedidosService;

    @Async("taskExecutor1")
    public CompletableFuture<Boolean> enviarCorreoAsync(){
        try{
            var result = envioCorreoService.enviarCorreo();
            return CompletableFuture.completedFuture(result);
        }catch (Exception ex){
            log.error("Error al enviar correo: {}", ex.getMessage());
             return CompletableFuture.completedFuture(false);
        }
    }
    @Async("taskExecutor1")
    public CompletableFuture<Integer> obtenerResumenUsuariosAsync(){
        try{
            var result = usuariosService.obtenerResumenUsuarios();
            return CompletableFuture.completedFuture(result);
        }catch (Exception ex){
            log.error("Error al obtener resumen de usuarios: {}", ex.getMessage());
             return CompletableFuture.completedFuture(0);
        }
    }

    @Async("taskExecutor2")
    public CompletableFuture<Integer> obtenerResumenPedidosAsync(){
        try{
            var result = pedidosService.obtenerResumenPedidos();
            return CompletableFuture.completedFuture(result);
        }catch (Exception ex){
            log.error("Error al obtener resumen de pedidos: {}", ex.getMessage());
             return CompletableFuture.completedFuture(0);
        }
    }
}
