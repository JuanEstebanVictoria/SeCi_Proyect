package co.edu.uniquindio.seci_proyect.data;


import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrect_thenNoViolations() {
        UserResponse response = new UserResponse(
                "12345",
                "test@example.com",
                "Test User",
                LocalDate.of(1990, 1, 1),
                Rol.USER,
                new GeoJsonPoint(-75.567, 6.234)
        );

        var violations = validator.validate(response);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenIdBlank_thenOneViolation() {
        UserResponse response = new UserResponse(
                "",
                "test@example.com",
                "Test User",
                LocalDate.of(1990, 1, 1),
                Rol.USER,
                new GeoJsonPoint(-75.567, 6.234)
        );

        var violations = validator.validate(response);
        assertEquals(1, violations.size());
    }

    @Test
    void whenEmailInvalid_thenOneViolation() {
        UserResponse response = new UserResponse(
                "12345",
                "invalid-email",
                "Test User",
                LocalDate.of(1990, 1, 1),
                Rol.USER,
                new GeoJsonPoint(-75.567, 6.234)
        );

        var violations = validator.validate(response);
        assertEquals(1, violations.size());
    }

    @Test
    void whenFullNameBlank_thenOneViolation() {
        UserResponse response = new UserResponse(
                "12345",
                "test@example.com",
                "",
                LocalDate.of(1990, 1, 1),
                Rol.USER,
                new GeoJsonPoint(-75.567, 6.234)
        );

        var violations = validator.validate(response);
        assertEquals(1, violations.size());
    }
}
