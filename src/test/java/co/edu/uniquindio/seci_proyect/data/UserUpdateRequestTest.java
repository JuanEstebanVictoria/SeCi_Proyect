package co.edu.uniquindio.seci_proyect.data;

import co.edu.uniquindio.seci_proyect.dtos.user.UserUpdateRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserUpdateRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        UserUpdateRequest request = new UserUpdateRequest(
                "Updated Name",
                new GeoJsonPoint(-75.567, 6.234),
                LocalDate.of(1990, 1, 1)
        );

        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenFutureDate_thenOneViolation() {
        UserUpdateRequest request = new UserUpdateRequest(
                "Updated Name",
                new GeoJsonPoint(-75.567, 6.234),
                LocalDate.now().plusDays(1)
        );

        var violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void whenNullValues_thenValid() {
        UserUpdateRequest request = new UserUpdateRequest(
                null,
                null,
                null
        );

        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEmptyName_thenValid() {
        UserUpdateRequest request = new UserUpdateRequest(
                "",
                new GeoJsonPoint(-75.567, 6.234),
                LocalDate.of(1990, 1, 1)
        );

        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}