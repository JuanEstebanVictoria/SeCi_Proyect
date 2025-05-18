package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.dtos.auth.*;
import co.edu.uniquindio.seci_proyect.dtos.user.PasswordResetRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    LoginResponse refreshToken(RefreshTokenRequest request);
    void requestPasswordReset(String email);
    void resetPassword(PasswordResetRequest request);
    LoginResponse register(RegisterRequest request);
}