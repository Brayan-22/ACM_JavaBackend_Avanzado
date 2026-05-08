package com.architecture.vertical.features.configurations;

import org.springframework.stereotype.Service;

@Service
public class SenderEmails {
    public void sendEmail(String destinatario, String asunto, String cuerpo){
        System.out.println("Sending email to: " + destinatario);
    }
}
