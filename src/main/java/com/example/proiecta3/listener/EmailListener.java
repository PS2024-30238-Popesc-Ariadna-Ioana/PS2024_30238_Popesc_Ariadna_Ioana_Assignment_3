package com.example.proiecta3.listener;

import com.example.proiecta3.dtos.EmailDto;
import com.example.proiecta3.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper; // Injecteaza ObjectMapper pt serializare JSON

    @RabbitListener(queues = "email-queue")
    public void processEmailRequest(String in) { // Receive message as a String
        try {
            EmailDto emailDto = objectMapper.readValue(in, EmailDto.class);
            emailService.sendEmail(emailDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}