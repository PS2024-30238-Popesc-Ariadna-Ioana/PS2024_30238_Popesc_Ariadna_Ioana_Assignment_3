package com.example.proiecta3.entity;

import com.example.proiecta3.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListener {

    //@RabbitListener(queues = "super-queue")
    /*public void listen(String message) {
        try {
            EmailService emailService = new EmailService();
            emailService.sendEmail();
        } catch (Exception ex) {
            System.err.println("An error occurred while processing the message: " + ex.getMessage());
        }
        System.out.println("Message read from super-queue: " + message);
    }*/
}

