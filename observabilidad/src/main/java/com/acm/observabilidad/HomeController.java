package com.acm.observabilidad;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
@Slf4j
public class HomeController {
    @GetMapping
    public ResponseEntity<?> home(){
        log.info("Hello endpoint called");
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/greet/{name}")
    public ResponseEntity<?> greet(@PathVariable String name){
        log.info("Greeting user: {}", name);
        simulateWork();
        return ResponseEntity.ok("Hello, " + name + "!");
    }

    @GetMapping("/slow")
    public ResponseEntity<?> slow() throws InterruptedException{
        log.info("Starting slow operation");
        Thread.sleep(500);
        log.info("Finished slow operation");
        return ResponseEntity.ok("This was a slow operation");
    }

    private void simulateWork(){
        try{
            Thread.sleep(50);
        }catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
}
