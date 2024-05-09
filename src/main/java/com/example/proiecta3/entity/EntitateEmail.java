package com.example.proiecta3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EntitateEmail {
    @Id
    @GeneratedValue(generator = "uuid")
    private String id;

    private String nume;

    private String subiect;

    private String email;

    private String body;
}
