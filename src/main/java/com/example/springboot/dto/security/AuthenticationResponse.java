package com.example.springboot.dto.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationResponse {
    private final String jwt;
    private final String username;
}
