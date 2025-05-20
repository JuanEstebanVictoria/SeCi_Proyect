package co.edu.uniquindio.seci_proyect.dtos.user;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserInitDto {
    private String email;
    private String password;
    private String rol;
    private String name;
    private LocalDate birthDate;
    private Location location;

    @Data
    public static class Location {
        private double latitude;
        private double longitude;
    }
}
