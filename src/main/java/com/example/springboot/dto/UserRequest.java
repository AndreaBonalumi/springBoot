package com.example.springboot.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long idUser;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private boolean admin = false;
    private String drivingLicense;
}
