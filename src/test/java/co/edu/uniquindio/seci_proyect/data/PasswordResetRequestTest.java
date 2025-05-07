package co.edu.uniquindio.seci_proyect.data;


import co.edu.uniquindio.seci_proyect.dtos.user.PasswordResetRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordResetRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrect_thenNoViolations() {
        PasswordResetRequest request = new PasswordResetRequest("user1@user.com","ABC123", "NewPassword123!");
        var violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenResetCodeBlank_thenOneViolation() {
        PasswordResetRequest request = new PasswordResetRequest("user1@user.com","ABC123", "NewPassword123!");
        var violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void whenNewPasswordBlank_thenOneViolation() {
        PasswordResetRequest request = new PasswordResetRequest("user1@user.com","ABC123", "");
        var violations = validator.validate(request);
        assertEquals(1, violations.size());
    }

    @Test
    void whenAllFieldsBlank_thenTwoViolations() {
        PasswordResetRequest request = new PasswordResetRequest("","", "");
        var violations = validator.validate(request);
        assertEquals(2, violations.size());
    }
}