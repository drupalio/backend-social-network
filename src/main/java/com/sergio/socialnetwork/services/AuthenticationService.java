package com.sergio.socialnetwork.services;

import java.nio.CharBuffer;

import com.sergio.socialnetwork.dto.CredentialsDto;
import com.sergio.socialnetwork.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto authenticate(CredentialsDto credentialsDto) {
        String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap("the-password"));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), encodedMasterPassword)) {
            return new UserDto(1L, "Sergio", "Lema", "token");
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto findByToken(String token) {
        if ("token".equals(token)) {
            return new UserDto(1L, "Sergio", "Lema", "token");
        }
        throw new RuntimeException("Invalid token");
    }
}
