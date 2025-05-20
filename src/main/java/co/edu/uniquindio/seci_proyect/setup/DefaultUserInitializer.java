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
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(defaultUser.getEmail());
        user.setFullName(defaultUser.getName());
        user.setPassword(passwordEncoder.encode(defaultUser.getPassword()));
        user.setDateBirth(defaultUser.getBirthDate());
        user.setRol(defaultUser.getRol());
        user.setStatus(UserStatus.ACTIVE);
        user.setLocation(new GeoJsonPoint(
                defaultUser.getLocation().getLongitude(),
                defaultUser.getLocation().getLatitude()
        ));
        return user;
    }
}
