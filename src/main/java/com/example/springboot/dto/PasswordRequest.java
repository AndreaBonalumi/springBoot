package com.example.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    String oldPassword;
    String newPassword;
    String newPasswordR;
}
