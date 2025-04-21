package co.edu.uniquindio.seci_proyect;

import co.edu.uniquindio.seci_proyect.setup.DefaultUserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DefaultUserProperties.class)
public class SeCiProyectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeCiProyectApplication.class, args);
    }

}
