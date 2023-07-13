package com.example.springboot.security.service;

import com.example.springboot.dao.request.SignUpRequest;
import com.example.springboot.dao.request.SigninRequest;
import com.example.springboot.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
