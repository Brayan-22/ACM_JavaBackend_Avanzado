package com.acm.microservicio2;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class Microservicio2Application {

    public static void main(String[] args) {
        SpringApplication.run(Microservicio2Application.class, args);
    }

    @Bean
    RestClient micro1RestClient(ObservationRegistry observationRegistry){
        return RestClient.builder()
                .baseUrl("http://localhost:8081")
                .observationRegistry(observationRegistry)
                .build();
    }

    @Bean
    Microservice1Exchange exchange(RestClient restClient){
        HttpServiceProxyFactory proxyFactory =
                HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return proxyFactory.createClient(Microservice1Exchange.class);
    }

    @Bean
    FilterRegistrationBean<JwtLoggingFilter> jwtLoggingFilterRegistration(JwtLoggingFilter jwtLoggingFilter) {
        FilterRegistrationBean<JwtLoggingFilter> registration = new FilterRegistrationBean<>(jwtLoggingFilter);
        registration.setOrder(1);
        return registration;
    }

}
