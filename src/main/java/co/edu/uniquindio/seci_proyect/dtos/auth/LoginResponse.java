package co.edu.uniquindio.seci_proyect.dtos.auth;

import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;

public record LoginResponse(
        String token,
        String refreshToken,
        UserResponse user
) {}