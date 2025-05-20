package co.edu.uniquindio.seci_proyect.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Collections;
import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "SrCi";
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Bean
    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(
                List.of(
                        new GeoJsonPointWriteConverter(),
                        new GeoJsonPointReadConverter()
                ) 
        );
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
