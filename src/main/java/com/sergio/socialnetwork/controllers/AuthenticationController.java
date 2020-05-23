package com.sergio.socialnetwork.controllers;

import java.net.URI;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.sergio.socialnetwork.dto.SignUpDto;
import com.sergio.socialnetwork.dto.UserDto;
import com.sergio.socialnetwork.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn(@AuthenticationPrincipal UserDto user) {
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.signUp(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId() + "/profile")).body(createdUser);
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDto user, HttpSession session) {
        userService.signOut(user);
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
