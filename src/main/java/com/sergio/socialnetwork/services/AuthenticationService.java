package com.sergio.socialnetwork.services;

import java.nio.CharBuffer;
import java.util.UUID;
import javax.transaction.Transactional;

import com.sergio.socialnetwork.dto.CredentialsDto;
import com.sergio.socialnetwork.dto.UserDto;
import com.sergio.socialnetwork.entities.User;
import com.sergio.socialnetwork.exceptions.AppException;
import com.sergio.socialnetwork.mappers.UserMapper;
import com.sergio.socialnetwork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public UserDto authenticate(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());

            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto findByToken(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Token not found", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }
}
