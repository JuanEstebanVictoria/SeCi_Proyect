package co.edu.uniquindio.seci_proyect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import co.edu.uniquindio.seci_proyect.Model.GeoJsonPointConverter;
import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(
                Arrays.asList(
                        new GeoJsonPointConverter() // Registra tu conversor de Point a GeoJsonPoint
                )
        );
    }
}