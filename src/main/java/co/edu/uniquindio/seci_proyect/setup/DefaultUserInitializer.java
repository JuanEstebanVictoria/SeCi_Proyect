package co.edu.uniquindio.seci_proyect.setup;



import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DefaultUserInitializer implements CommandLineRunner {
    private final DefaultUserProperties defaultUserProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            defaultUserProperties.getUsers().stream()
                    .map(this::createUser).forEach(userRepository::save);
        }
    }

    private User createUser(DefaultUserProperties.DefaultUser defaultUser) {
        return new User(
                UUID.randomUUID().toString(),
                defaultUser.username(),                     
                defaultUser.username(),
                passwordEncoder.encode(defaultUser.password()),
                LocalDate.of(1982, 8, 27),
                defaultUser.role(),
                UserStatus.ACTIVE,
                new GeoJsonPoint(0.0, 0.0)
        );
    }
}
