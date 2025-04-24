package co.edu.uniquindio.seci_proyect.data;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestDataLoader {
    public static Map<String, User> loadTestData(UserRepository userRespository, MongoTemplate mongoTemplate) {
        return loadTestData(
                List.of(
                        new User(UUID.randomUUID().toString(),"ana@example.com", "Ana López","{noop}12346Abc", LocalDate.of(1982,8,27), Rol.USER, UserStatus.ACTIVE, new GeoJsonPoint(-75.6801, 4.8156)),
                        new User(UUID.randomUUID().toString(),"carlos@example.com","Carlos Pérez","{noop}12346Abc", LocalDate.of(1984,10,28), Rol.USER, UserStatus.ACTIVE,  new GeoJsonPoint(-74.6801, 4.71110)),
                        new User(UUID.randomUUID().toString(),"juan@example.com","Juan Root","{noop}12346Abc", LocalDate.of(1984,10,28), Rol.ADMIN, UserStatus.ACTIVE, new GeoJsonPoint(-76.6801, 4.8156))
                ),
                userRespository,
                mongoTemplate
        );
    }

    public static Map<String, User> loadTestData(Collection<User> newUsers, UserRepository userRespository, MongoTemplate mongoTemplate) {
        // Borrar datos existentes para asegurar la repetibilidad de las pruebas.
        mongoTemplate.getDb().listCollectionNames()
                .forEach(mongoTemplate::dropCollection);
        return userRespository.saveAll(newUsers).stream().collect(Collectors.toMap(User::getId, usuario -> usuario));
    }
}