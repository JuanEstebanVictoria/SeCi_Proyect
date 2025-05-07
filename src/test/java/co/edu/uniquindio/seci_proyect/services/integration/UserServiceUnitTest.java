package co.edu.uniquindio.seci_proyect.services.integration;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.dtos.user.*;
import co.edu.uniquindio.seci_proyect.exceptions.ActivationException;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.UserMapper;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.services.impl.UserServiceImpl;
import co.edu.uniquindio.seci_proyect.services.interfaces.EmailService;
import co.edu.uniquindio.seci_proyect.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserResponse testUserResponse;
    private UserRegistrationRequest registrationRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId("123");
        testUser.setEmail("test@example.com");
        testUser.setStatus(UserStatus.REGISTERED);

        testUserResponse = new UserResponse(
                "123", "test@example.com", "Test User",
                LocalDate.of(1990, 1, 1), Rol.USER, new GeoJsonPoint(0, 0)
        );

        registrationRequest = new UserRegistrationRequest(
                "test@example.com", "Test User", "Password123!",
                LocalDate.of(1990, 1, 1), Rol.USER, UserStatus.ACTIVE,
                new GeoJsonPoint(0, 0)
        );
    }

    @Test
    void createUser_NewEmail_ReturnsUserResponse() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.parseOf(any())).thenReturn(testUser);
        when(userRepository.save(any())).thenReturn(testUser);
        when(userMapper.toUserResponse(any())).thenReturn(testUserResponse);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        UserResponse result = userService.createUser(registrationRequest);

        assertNotNull(result);
        verify(emailService).sendActivationEmail(anyString(), anyString());
    }

    @Test
    void createUser_ExistingEmail_ThrowsException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(testUser));

        assertThrows(ValueConflictException.class,
                () -> userService.createUser(registrationRequest));
    }

    @Test
    void activateUser_ValidCode_ActivatesUser() {
        testUser.setActivationCode("VALID");
        testUser.setActivationCodeExpiry(LocalDateTime.now().plusHours(1));

        when(userRepository.findByActivationCode(anyString())).thenReturn(Optional.of(testUser));

        userService.activateUser("VALID");

        assertEquals(UserStatus.ACTIVE, testUser.getStatus());
        assertNull(testUser.getActivationCode());
        verify(userRepository).save(testUser);
    }


}