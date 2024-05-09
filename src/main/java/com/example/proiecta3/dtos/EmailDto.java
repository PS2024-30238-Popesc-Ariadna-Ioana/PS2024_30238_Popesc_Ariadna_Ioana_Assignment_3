package com.example.proiecta3.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDto implements Serializable {
    @NotNull
    private String id;

    @NotBlank
    private String nume;

    @NotNull
    private String subiect;

    @NotNull
    @Email(message = "The format for the email is invalid!!!")
    private String email;

    @NotNull
    private String body;
}
