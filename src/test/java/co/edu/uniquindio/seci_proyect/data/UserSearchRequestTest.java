package co.edu.uniquindio.seci_proyect.data;

import co.edu.uniquindio.seci_proyect.dtos.user.UserSearchRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserSearchRequestTest {

    @Test
    void whenNullValues_thenDefaultApplied() {
        UserSearchRequest request = new UserSearchRequest(null, null, null, null, null);

        assertEquals(".*", request.fullName());
        assertEquals(".*", request.email());
        assertEquals(0, request.page());
        assertEquals(10, request.size());
    }

    @Test
    void whenEmptyValues_thenDefaultApplied() {
        UserSearchRequest request = new UserSearchRequest("", "", null, null, null);

        assertEquals(".*", request.fullName());
        assertEquals(".*", request.email());
    }

    @Test
    void whenValidValues_thenKeepsOriginalValues() {
        LocalDate date = LocalDate.of(1990, 1, 1);
        UserSearchRequest request = new UserSearchRequest("John", "john@test.com", date, 1, 20);

        assertEquals("John", request.fullName());
        assertEquals("john@test.com", request.email());
        assertEquals(date, request.dateBirth());
        assertEquals(1, request.page());
        assertEquals(20, request.size());
    }

    @Test
    void whenPartialValues_thenCorrectBehavior() {
        UserSearchRequest request = new UserSearchRequest("John", null, null, null, 15);

        assertEquals("John", request.fullName());
        assertEquals(".*", request.email());
        assertNull(request.dateBirth());
        assertEquals(0, request.page());
        assertEquals(15, request.size());
    }
}
