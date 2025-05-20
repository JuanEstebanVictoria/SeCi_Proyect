package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.dtos.auth.*;
import co.edu.uniquindio.seci_proyect.dtos.user.PasswordResetRequest;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import co.edu.uniquindio.seci_proyect.exceptions.*;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.security.*;
import co.edu.uniquindio.seci_proyect.services.interfaces.AuthService;
import co.edu.uniquindio.seci_proyect.services.interfaces.EmailService;
import co.edu.uniquindio.seci_proyect.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse register(RegisterRequest request) {
        // 1. Validar si el email ya existe
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessRuleException("El correo ya está registrado");
        }

        // 2. Crear un nuevo usuario con los datos del request
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setFullName(request.nombre()); // Ajusta según campos que tengas en RegisterRequest y User
        newUser.setRol(request.rol());           // Asegúrate que rol viene en el request o asigna uno por defecto
        newUser.setStatus(UserStatus.ACTIVE);    // Ejemplo, ajusta según tu enum y lógica

        // Guardar usuario en la base de datos
        userRepository.save(newUser);

        // 3. Autenticar al usuario recién creado para generar tokens
        try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. Obtener datos para la respuesta
        UserResponse userResponse = userService.getUserByEmail(request.email());

        String jwt = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        // 5. Retornar LoginResponse con los tokens y datos del usuario
        return new LoginResponse(jwt, refreshToken, userResponse);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Credenciales inválidas");
        }
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserResponse user = userService.getUserByEmail(request.email());
            String jwt = tokenProvider.generateToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);

            return new LoginResponse(jwt, refreshToken, user);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Credenciales inválidas");
        }
    }
    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        if (!tokenProvider.validateToken(request.refreshToken())) {
            throw new TokenException("Refresh token inválido o expirado");
        }

        String username = tokenProvider.getUsernameFromToken(request.refreshToken());
        UserResponse user = userService.getUserByEmail(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.email(),
                null,
                userService.getAuthorities(user.rol())
        );

        String newToken = tokenProvider.generateToken(authentication);
        return new LoginResponse(newToken, request.refreshToken(), user);
    }

    @Override
    public void requestPasswordReset(String email) {
        // 1. Buscar al usuario usando el método del repositorio
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // 2. Generar y asignar código de recuperación (método ya existe en User)
        user.generateAndSetResetPasswordCode();
        userRepository.save(user);

        // 3. Enviar email con el código
        emailService.sendEmail(
                email,
                "Recuperación de Contraseña - Seguridad Ciudadana",
                "Hola " + user.getFullName() + ",\n\n" +
                        "Tu código de recuperación es: **" + user.getResetPasswordCode() + "**\n" +
                        "Válido por 15 minutos. No lo compartas con nadie.\n\n" +
                        "Si no solicitaste esto, ignora este mensaje."
        );
    }

    @Override
    public void resetPassword(PasswordResetRequest request) {
        // 1. Buscar al usuario por email y código (usando consulta del repositorio)
        User user = userRepository.findByResetPasswordCode(request.code())
                .filter(u -> u.getEmail().equals(request.email())) // Verificar coincidencia de email
                .orElseThrow(() -> new BusinessRuleException("Código inválido o usuario no encontrado"));

        // 2. Validar expiración del código (usando método de User)
        if (!user.isResetPasswordCodeValid()) {
            throw new BusinessRuleException("El código ha expirado");
        }

        // 3. Actualizar contraseña (encriptada) y limpiar código
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setResetPasswordCode(null);
        user.setResetPasswordCodeExpiry(null);
        userRepository.save(user);

        // 4. Notificar al usuario
        emailService.sendEmail(
                user.getEmail(),
                "Contraseña Actualizada - Seguridad Ciudadana",
                "Hola " + user.getFullName() + ",\n\n" +
                        "Tu contraseña ha sido actualizada exitosamente.\n" +
                        "Si no realizaste este cambio, contacta al soporte inmediatamente."
        );
    }

}