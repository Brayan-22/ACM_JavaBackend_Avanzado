package com.acm.microservicio1;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@SpringBootApplication
public class ObservabilidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObservabilidadApplication.class, args);
	}

	@Bean
	RestClient micro2RestClient(ObservationRegistry observationRegistry, JwtClientInterceptor jwtClientInterceptor){
		return RestClient.builder()
				.observationRegistry(observationRegistry)
				.requestInterceptor(jwtClientInterceptor)
				.baseUrl("http://localhost:8082")
				.build();
	}

	@Bean
	Microservice2Exchange exchange(RestClient restClient){
		HttpServiceProxyFactory proxyFactory =
				HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return proxyFactory.createClient(Microservice2Exchange.class);
	}

}
