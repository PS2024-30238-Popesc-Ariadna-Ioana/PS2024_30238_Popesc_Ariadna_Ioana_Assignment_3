package com.example.proiecta3.controller;

import com.example.proiecta3.dtos.EmailDto;
import com.example.proiecta3.dtos.MessageDto;
import com.example.proiecta3.service.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailController {
    public static final String FIRST_TOKEN = "9d7b1f62-28e3-4a15-9c98-3ec9f8e723f1";
    public static final String SECOND_TOKEN = "7f9da720-5148-4b47-9eb7-69e3ae11d8af";

    // 9d7b1f62-28e3-4a15-9c98-3ec9f8e723f17f9da720-5148-4b47-9eb7-69e3ae11d8af
    private final EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<MessageDto> sendEmail(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody EmailDto emailDto,
            BindingResult bindingResult) {

        MessageDto messageDto = new MessageDto();

        if(!isTokenValid(token)){
            messageDto.setMessage("Invalid authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
        }

        if(bindingResult.hasErrors()){
            messageDto.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
        }

        emailService.sendEmail(emailDto);
        messageDto.setMessage("Email successfully sent!");
        return ResponseEntity.status(HttpStatus.OK).body(messageDto);
    }

    private boolean isTokenValid(String token){
        String validToken = FIRST_TOKEN + SECOND_TOKEN;
        String t = token.substring(7);
        return validToken.equals(t);
    }
}
