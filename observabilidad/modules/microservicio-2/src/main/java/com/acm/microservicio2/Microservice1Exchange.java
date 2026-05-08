package com.acm.microservicio2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/v1/micro1/")
public interface Microservice1Exchange {
    @GetExchange("/callable")
    ResponseEntity<String> callingMicro1();
}
