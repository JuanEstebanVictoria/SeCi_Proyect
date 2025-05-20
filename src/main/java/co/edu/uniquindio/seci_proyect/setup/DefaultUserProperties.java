package co.edu.uniquindio.seci_proyect.setup;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "default-users")
@NoArgsConstructor
public class DefaultUserProperties {
    private List<DefaultUser> users;

    @Setter
    @NoArgsConstructor
    @Getter
    public static class DefaultUser {
        private String email;
        private String password;
        private Rol rol;
        private String name;
        private LocalDate birthDate;
        private Location location;
    }

    @Setter
    @NoArgsConstructor
    @Getter
    public static class Location {
        private double latitude;
        private double longitude;
    }
}