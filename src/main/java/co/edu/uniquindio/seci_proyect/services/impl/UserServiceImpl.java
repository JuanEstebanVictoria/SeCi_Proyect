package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.dtos.user.*;
import co.edu.uniquindio.seci_proyect.exceptions.ActivationException;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.UserMapper;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.EmailService;
import co.edu.uniquindio.seci_proyect.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRegistrationRequest user) {
        if(userRepository.findUserByEmail(user.email()).isPresent()){
            throw new ValueConflictException("Email already registered");
        }
        var newUser = userMapper.parseOf(user);
        newUser.setStatus(UserStatus.REGISTERED);
        newUser.setActivationCode(generateActivationCode());
        newUser.setActivationCodeExpiry(LocalDateTime.now().plusMinutes(15));

        User savedUser = userRepository.save(newUser);

        // Enviar email de activación
        emailService.sendActivationEmail(savedUser.getEmail(), savedUser.getActivationCode());
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public Optional<UserResponse> getUser(String id) {
        return userRepository.findById(id).map(userMapper::toUserResponse);
    }

    @Override
    public Page<UserResponse> searchUsers(UserSearchRequest request) {
        // configurar paginacion
        Pageable pageable = PageRequest.of(request.page(), request.size());

        // llamar al repositorio con filtros
        return userRepository.findExistingUsersByFilters(
                request.fullName(),
                request.email(),
                request.dateBirth(),
                pageable
        ).map(userMapper::toUserResponse);
    }

    //completar
    @Override
    @Transactional
    public void activateUser(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Código de activación no válido"));

        if (user.getActivationCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new ActivationException("El código de activación ha expirado");
        }

        if (user.getStatus() == UserStatus.ACTIVE) {
            throw new ActivationException("La cuenta ya está activa");
        }
        user.setStatus(UserStatus.ACTIVE);
        user.setActivationCode(null);
        user.setActivationCodeExpiry(null);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Actualización condicional de campos
        boolean needsUpdate = false;

        if (request.fullName() != null && !request.fullName().equals(user.getFullName())) {
            user.setFullName(request.fullName());
            needsUpdate = true;
        }
        if (request.dateBirth() != null && !request.dateBirth().equals(user.getDateBirth())) {
            user.setDateBirth(request.dateBirth());
            needsUpdate = true;
        }
        if (request.location() != null && !request.location().equals(user.getLocation())) {
            user.setLocation(request.location());
            needsUpdate = true;
        }
        // Solo guardar si hubieron cambios reales
        if (needsUpdate) {
            user = userRepository.save(user);
        }

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        String resetCode = generateActivationCode();
        user.setResetPasswordCode(resetCode);
        user.setResetPasswordCodeExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), resetCode);
    }


    @Override
    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        User user = userRepository.findByResetPasswordCode(request.resetCode())
                .orElseThrow(() -> new ResourceNotFoundException("Código de restablecimiento no válido"));

        if (user.getResetPasswordCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new ActivationException("El código de restablecimiento ha expirado");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setResetPasswordCode(null);
        user.setResetPasswordCodeExpiry(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserById(String id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    @Transactional
    public String deleteUser(String id) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
        return "Usuario eliminado exitosamente";
    }

    @Override
    @Transactional
    public String activateAccount(String activationCode) throws Exception {
        activateUser(activationCode);
        return "Cuenta activada exitosamente";
    }

    private String generateActivationCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
