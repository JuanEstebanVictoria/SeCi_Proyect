package co.edu.uniquindio.seci_proyect.dtos.user;

import jakarta.validation.constraints.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;

public record UserUpdateRequest(
        String fullName,
        GeoJsonPoint location
) {}