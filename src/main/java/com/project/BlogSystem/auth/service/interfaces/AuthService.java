package com.project.BlogSystem.auth.service.interfaces;

import com.project.BlogSystem.auth.dto.*;

// Interface for the authentication service
public interface AuthService {
    AuthResponse registerUser(RegisterRequest registerRequest);

    AuthResponse loginUser(LoginRequest LoginRequest);

}
