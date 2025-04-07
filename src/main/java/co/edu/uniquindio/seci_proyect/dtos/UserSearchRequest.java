package co.edu.uniquindio.seci_proyect.dtos;

import java.time.LocalDate;
import java.util.Objects;

public record UserSearchRequest(String fullName,
                                String email,
                                LocalDate dateBirth,
                                Integer page,
                                Integer size) {

    public UserSearchRequest{
        fullName= verifyvalue(fullName);
        email= verifyvalue(email);
        page = Objects.requireNonNullElse(page,0);
        size= Objects.requireNonNullElse(size, 10);
    }

    private String verifyvalue(String value) {
        return value == null || value.isBlank() ? ".*" : value;
    }
}
