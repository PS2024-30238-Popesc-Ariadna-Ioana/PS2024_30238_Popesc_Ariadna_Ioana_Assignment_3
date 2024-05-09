package com.example.proiecta3.service;

import com.example.proiecta3.dtos.EmailDto;
import com.example.proiecta3.entity.EntitateEmail;
import com.example.proiecta3.repositories.EmailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ObjectMapper objectMapper;

    private final EmailRepository emailRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public class EmailTemplateLoader {
        public String loadTemplate(String filePath) {
            try {
                return new String(Files.readAllBytes(Paths.get(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void sendEmail(EmailDto emailDto) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(emailDto);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("popescariadna@gmail.com", "Flowershop");
            helper.setTo(emailDto.getEmail());
            helper.setSubject(emailDto.getSubiect());
            EmailTemplateLoader loader = new EmailTemplateLoader();
            String htmlTemplate = null;
            try {
                htmlTemplate = StreamUtils.copyToString(
                        new ClassPathResource("templates/email.html").getInputStream(),
                        StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String modifiedHtmlContent = htmlTemplate.replace("$BODY_CONTENT$", emailDto.getBody());
            helper.setText(modifiedHtmlContent, true);

            emailSender.send(message);

            EntitateEmail email = new EntitateEmail();
            email.setNume(emailDto.getNume());
            email.setSubiect(emailDto.getSubiect());
            email.setEmail(emailDto.getEmail());
            email.setBody(emailDto.getBody());
            emailRepository.save(email);

        } catch (JsonProcessingException | MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
