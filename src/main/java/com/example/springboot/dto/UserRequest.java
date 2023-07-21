package com.example.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long idUser;
    private String username;
    @Pattern(regexp = ".*\\d.*", message = "la password deve contenere almeno un numero")
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private boolean admin = false;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "la patente deve contenere solo lettere")
    private String drivingLicense;
    private Long createdBy;
}
