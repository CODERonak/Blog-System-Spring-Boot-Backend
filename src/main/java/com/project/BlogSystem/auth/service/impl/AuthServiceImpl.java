package com.project.BlogSystem.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.BlogSystem.auth.dto.AuthResponse;
import com.project.BlogSystem.auth.dto.LoginRequest;
import com.project.BlogSystem.auth.dto.RegisterRequest;
import com.project.BlogSystem.auth.exceptions.InvalidCredentialsException;
import com.project.BlogSystem.auth.exceptions.UsernameAlreadyExistsException;
import com.project.BlogSystem.auth.mapper.AuthMapper;
import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.auth.repository.AuthUserRepository;
import com.project.BlogSystem.auth.security.jwt.JWTUtil;
import com.project.BlogSystem.auth.service.interfaces.AuthService;

import lombok.AllArgsConstructor;

/*

Implements user register and login with returning the JWT token.

AuthServiceImpl implements AuthService which contains two methods:
* AuthResponse registerUser(RegisterRequest registerRequest);
* AuthResponse loginUser(LoginRequest LoginRequest);

*/

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthUserRepository authUserRepository;
    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registerUser(RegisterRequest registerRequest) {

        if (authUserRepository.existsByUsername(registerRequest.getUsername()))
            throw new UsernameAlreadyExistsException("Username already exists, use another one");

        if (authUserRepository.existsByEmail(registerRequest.getEmail()))
            throw new UsernameAlreadyExistsException("Email already exists, use another one");

        AuthUser user = authMapper.registerRequestToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        authUserRepository.save(user);

        String jwtToken = jwtUtil.generateToken(registerRequest.getUsername());

        return AuthResponse.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            // sets the authentication
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtil.generateToken(loginRequest.getUsername());

            return AuthResponse.builder()
                    .username(loginRequest.getUsername())
                    .token(jwtToken)
                    .build();

        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }

}
