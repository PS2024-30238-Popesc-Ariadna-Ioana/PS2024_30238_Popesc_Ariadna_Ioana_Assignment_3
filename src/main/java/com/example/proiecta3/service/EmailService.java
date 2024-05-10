package com.example.proiecta3.service;

import com.example.proiecta3.dtos.EmailDto;
import com.example.proiecta3.entity.EntitateEmail;
import com.example.proiecta3.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@AllArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final EmailRepository emailRepository;

    public void sendEmail(EmailDto emailDto) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("popescariadna@gmail.com", "Flowershop");
            helper.setTo(emailDto.getEmail());
            helper.setSubject(emailDto.getSubiect());

            String modifiedHtmlContent = loadAndModifyTemplate("templates/email.html", emailDto.getBody());
            helper.setText(modifiedHtmlContent, true);

            emailSender.send(message);

            EntitateEmail email = new EntitateEmail();
            email.setNume(emailDto.getNume());
            email.setSubiect(emailDto.getSubiect());
            email.setEmail(emailDto.getEmail());
            email.setBody(emailDto.getBody());
            emailRepository.save(email);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private String loadAndModifyTemplate(String filePath, String bodyContent) throws IOException {
        String htmlTemplate = StreamUtils.copyToString(
                new ClassPathResource(filePath).getInputStream(),
                StandardCharsets.UTF_8);
        return Objects.requireNonNull(htmlTemplate).replace("$BODY_CONTENT$", bodyContent);
    }
}
