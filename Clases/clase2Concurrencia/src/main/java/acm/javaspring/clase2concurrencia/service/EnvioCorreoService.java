package acm.javaspring.clase2concurrencia.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@Slf4j
public class EnvioCorreoService {

    public boolean enviarCorreo(){
        try{
            log.info("Enviando correo...");
            Thread.sleep(new Random().nextInt(5000));
            log.info("Correo enviado exitosamente");
        }catch (Exception ex){
            log.error("Error al enviar correo: {}", ex.getMessage());
             return false;
        }
        return true;
    }

}
