package com.example.springboot.services.impl;

import com.example.springboot.repositories.UserRepository;
import com.example.springboot.dto.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username)
                .map(user -> UserSecurity.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .isAdmin(user.isAdmin())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }
}
