package com.example.proiecta3.dtos;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    private String status;

    private String message;
}
