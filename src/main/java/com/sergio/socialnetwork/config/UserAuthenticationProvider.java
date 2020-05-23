package com.sergio.socialnetwork.config;

import java.util.Collections;

import com.sergio.socialnetwork.dto.CredentialsDto;
import com.sergio.socialnetwork.dto.UserDto;
import com.sergio.socialnetwork.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            String login = authentication.getPrincipal().toString();
            char[] password = (char[]) authentication.getCredentials();
            UserDto user = authenticationService.authenticate(new CredentialsDto(login, password));
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            return new UsernamePasswordAuthenticationToken(
                    authenticationService.findByToken(
                            authentication.getPrincipal().toString()), null, Collections.emptyList());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
