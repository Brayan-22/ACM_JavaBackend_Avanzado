package com.acm.microservicio2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/v1/micro2")
@Slf4j
public class HomeController {
    @Autowired
    private Microservice1Exchange exchange;

    @GetMapping("/normal")
    public ResponseEntity<?> normalEndpoint(){
        log.info("Normal endpoint called");
        return ResponseEntity.ok("Hello World from Microservice 2");
    }

    @GetMapping("/slow")
    public ResponseEntity<?> slowEndpoint(){
        log.info("Starting slow operation in Microservice 2");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        log.info("Finished slow operation in Microservice 2");
        return ResponseEntity.ok("This was a slow operation in Microservice 2");
    }

    @GetMapping("/call-other/")
    public ResponseEntity<?> callingOtherMicroservice(){
        log.info("Calling another microservice from Microservice 2");
        var response = exchange.callingMicro1();
        log.info("Received response from Microservice 1: {}", response);
        return ResponseEntity.ok("Called another microservice from Microservice 2");
    }

    @GetMapping("/callable")
    public ResponseEntity<String> endpointCallableForOtherMicro(@RequestParam("param") String param){
        log.info("Endpoint callable for other microservice called in Microservice 2 with param {}", param);
        long init = System.currentTimeMillis();
        try{
            Thread.sleep(new Random().nextInt(1000));
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        long finalTime = System.currentTimeMillis() - init;
        log.info("Finished endpoint callable for other microservice in Microservice 2, took {} ms", finalTime);
        return ResponseEntity.ok(String.format("This endpoint simulates a call from another microservice and took %d ms", finalTime));
    }
}
