package com.acm.microservicio1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/micro1")
@Slf4j
public class HomeController {

    @Autowired
    private Microservice2Exchange exchange;

    @GetMapping("/normal")
    public ResponseEntity<?> normalEndpoint(){
        log.info("Normal endpoint called");
        return ResponseEntity.ok("Hello World from Microservice 1");
    }

    @GetMapping("/slow")
    public ResponseEntity<?> slowEndpoint(){
        log.info("Starting slow operation in Microservice 1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        log.info("Finished slow operation in Microservice 1");
        return ResponseEntity.ok("This was a slow operation in Microservice 1");
    }

    @GetMapping("/call-other/{param}")
    public ResponseEntity<?> callingOtherMicroservice(@PathVariable String param){
        log.info("Calling another microservice from Microservice 1");
        var response = exchange.callingMicro2(param);
        log.info("Received response from Microservice 2: {}", response);
        return ResponseEntity.ok("Called another microservice from Microservice 1");
    }

    @GetMapping("/callable")
    public ResponseEntity<String> endpointCallableForOtherMicro(){
        log.info("Endpoint callable for other microservice called in Microservice 1");
        long init = System.currentTimeMillis();
        try{
            Thread.sleep(new Random().nextInt(2000));
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        long finalTime = System.currentTimeMillis() - init;
        log.info("Finished endpoint callable for other microservice in Microservice 1, took {} ms", finalTime);
        return ResponseEntity.ok(String.format("This endpoint simulates a call from another microservice and took %d ms", finalTime));
    }
}

