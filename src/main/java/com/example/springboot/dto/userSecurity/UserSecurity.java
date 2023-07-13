package com.example.springboot.dto.userSecurity;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
public class UserSecurity implements UserDetails {
    private final String username;
    private final String password;
    private final boolean isAdmin;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return isAdmin ? List.of(new SimpleGrantedAuthority("ADMIN")) : List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return username;
    }

    @Override
    public String getUsername() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
