package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long idUser;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private boolean admin = false;
    private String nPatente;
}
