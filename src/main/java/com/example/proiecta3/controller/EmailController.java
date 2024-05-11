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
    public static final String token1 = "e7f92c1b-2a9e-4f08-89fb-4c36e7e3bf2a";
    public static final String token2 = "a3b4d098-6c1e-45fb-a9f2-cb68f9e2d1a5";
    public static final String validToken = token1 + token2;

    // e7f92c1b-2a9e-4f08-89fb-4c36e7e3bf2aa3b4d098-6c1e-45fb-a9f2-cb68f9e2d1a5
    private final EmailService emailService;

    private boolean isTokenValid(String token){
        String t = token.substring(7);
        return validToken.equals(t);
    }

    @PostMapping("/sendEmail")
    public ResponseEntity<MessageDto> sendEmailToExternalService(@RequestHeader("Authorization") String token, @Valid @RequestBody EmailDto emailDto, BindingResult bindingResult) {

        MessageDto messageDto = new MessageDto();

        if(!isTokenValid(token)){
            messageDto.setMessage("Invalid authorization token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(messageDto);
        }

        if(bindingResult.hasErrors()){
            messageDto.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageDto);
        }

        emailService.sendEmailToExternalService(emailDto);
        messageDto.setMessage("Email successfully sent!");
        return ResponseEntity.status(HttpStatus.OK).body(messageDto);
    }
}
