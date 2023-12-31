package com.example.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long idUser;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private boolean admin;
    private String drivingLicense;
    private Long createdBy;
    private String profilePhoto;
}
