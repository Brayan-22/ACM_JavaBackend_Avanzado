package com.architecture.vertical.features.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfiguration {

    @Bean
    public SenderEmails senderEmails(@Value("${ipSmtp}") String ipSmtp) {
        return new SenderEmails();
    }
}
