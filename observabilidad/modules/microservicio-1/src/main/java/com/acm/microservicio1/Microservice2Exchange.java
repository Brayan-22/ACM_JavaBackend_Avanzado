package com.acm.microservicio1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/v1/micro2/")
public interface Microservice2Exchange {
    @GetExchange("/callable")
    ResponseEntity<String> callingMicro2(@RequestParam("param") String param);
}
