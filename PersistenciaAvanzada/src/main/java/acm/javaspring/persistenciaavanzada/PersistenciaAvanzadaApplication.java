package acm.javaspring.persistenciaavanzada;

import acm.javaspring.persistenciaavanzada.persistence.entity.CustomerEntity;
import acm.javaspring.persistenciaavanzada.persistence.repository.CustomerJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersistenciaAvanzadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersistenciaAvanzadaApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(CustomerJpaRepository customerRepository) {
        return args -> {
            if (customerRepository.count() == 0) {
                customerRepository.save(CustomerEntity.builder()
                        .fullName("Ana Torres")
                        .email("ana@example.com")
                        .build());

                customerRepository.save(CustomerEntity.builder()
                        .fullName("Luis García")
                        .email("luis@example.com")
                        .build());
            }
        };
    }
}
