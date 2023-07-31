package com.example.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long idUser;
    private String username;
    @Pattern(regexp = ".*\\d.*", message = "la password deve contenere almeno un numero")
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private boolean admin = false;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "la patente deve contenere solo lettere")
    private String drivingLicense;
    private Long createdBy;
    private String profilePhoto;
}
