package com.sergio.socialnetwork.services;

import java.nio.CharBuffer;
import java.util.UUID;

import javax.transaction.Transactional;

import com.sergio.socialnetwork.dto.CredentialsDto;
import com.sergio.socialnetwork.dto.UserDto;
import com.sergio.socialnetwork.entities.User;
import com.sergio.socialnetwork.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDto authenticate(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());

            return new UserDto(user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getToken());
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDto findByToken(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getToken());
    }
}
