package co.edu.uniquindio.seci_proyect.controllers;

import co.edu.uniquindio.seci_proyect.dtos.auth.*;
import co.edu.uniquindio.seci_proyect.dtos.user.PasswordResetRequest;
import co.edu.uniquindio.seci_proyect.services.interfaces.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /users
    @PostMapping("/users")
    public ResponseEntity<LoginResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // POST /login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // POST /refresh
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    // POST /password-reset/request
    @PostMapping("/password-reset/request")
    public ResponseEntity<String> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset email sent");
    }
}