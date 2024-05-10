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
    public static final String token1 = "1f72e35c-4e7b-45c9-a671-4c24f77e40f9";
    public static final String token2 = "3bdc0f64-b7d8-4b26-a72a-54fc54cb1efc";

    // 1f72e35c-4e7b-45c9-a671-4c24f77e40f93bdc0f64-b7d8-4b26-a72a-54fc54cb1efc
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
        String validToken = token1 + token2;
        String t = token.substring(7);
        return validToken.equals(t);
    }
}
