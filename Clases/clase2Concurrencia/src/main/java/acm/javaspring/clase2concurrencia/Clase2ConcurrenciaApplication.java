package acm.javaspring.clase2concurrencia;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.VirtualThreadExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestClient;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableAsync
@Slf4j
public class Clase2ConcurrenciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Clase2ConcurrenciaApplication.class, args);
    }

    @Bean
    public RestClient restClient(RestClient.Builder builder){
        return builder
                .build();
    }
    @Bean("taskExecutor1")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        log.info("Número de procesadores disponibles: {}", corePoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(corePoolSize * 2);
        executor.setQueueCapacity(corePoolSize * 2);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("-Async-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }
    @Bean("taskExecutor2")
    public Executor getAsyncExecutor2() {
        Executor executor = Executors.newVirtualThreadPerTaskExecutor();
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        log.info("Número de procesadores disponibles: {}", corePoolSize);
        return executor;
    }
}


