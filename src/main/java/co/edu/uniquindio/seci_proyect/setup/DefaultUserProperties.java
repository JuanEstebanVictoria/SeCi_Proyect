package co.edu.uniquindio.seci_proyect.setup;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "default-users")
@RequiredArgsConstructor(onConstructor_ = @ConstructorBinding)
public class DefaultUserProperties {
    private final List<DefaultUser> users;
    public record DefaultUser(String username, String password, Rol role) {}
}