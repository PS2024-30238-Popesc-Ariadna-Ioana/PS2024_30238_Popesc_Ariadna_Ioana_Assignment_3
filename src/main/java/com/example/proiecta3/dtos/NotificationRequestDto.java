package com.example.proiecta3.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDto {
    private String id;

    private String nume;

    private String prenume;

    private String email;

    private String body;
}
