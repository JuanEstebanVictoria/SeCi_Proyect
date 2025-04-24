package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.data.TestDataLoader;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private Map<String, User> users;

    @BeforeEach
    void setUp() {
        users = TestDataLoader.loadTestData(userRepository,mongoTemplate);
    }

    @Test
    void testFindUserByEmailSuccess() {
        // Sección de Arrange: Obtiene un usuario aleatorio a ser empleado en la prueba
        var testUser = users.values().stream().findAny().orElseThrow();
        // Sección de Act: Se ejecuta la búsqueda de usuario por email.
        Optional<User> result = userRepository.findUserByEmail(testUser.getEmail());

        // Sección de Assert: Se verifica que el usuario obtenido corresponda con los datos esperados.
        assertTrue(result.isPresent());
        assertEquals(testUser.getFullName(), result.get().getFullName());
    }

    @Test
    void testFindExistingUserByEmailWhenUserExists() {
        // Sección de Arrange: Obtiene un usuario aleatorio a ser empleado en la prueba
        var testUser = users.values().stream().findAny().orElseThrow();
        // Sección de Act: Se ejecuta la búsqueda de usuario por email con estado diferente a DELETED.
        Optional<User> result = userRepository.findExistingUserByEmail(testUser.getEmail());

        // Sección de Assert: Se verifica que el usuario obtenido corresponda con los datos esperados.
        assertTrue(result.isPresent());
        assertEquals(testUser.getEmail(), result.get().getEmail());
    }

    @Test
    void testFindExistingUserByEmailWhenUserDeleted() {
        // Sección de Arrange: Obtiene un usuario aleatorio a ser empleado en la prueba
        var testUser = users.values().stream().findAny().orElseThrow();
        testUser.setStatus(UserStatus.DELETED);
        userRepository.save(testUser);
        // Sección de Act: Se ejecuta la búsqueda de usuario por email con estado diferente a DELETED.
        Optional<User> result = userRepository.findExistingUserByEmail(testUser.getEmail());
        // Sección de Assert: Se verifica que el usuario no sea obtenido porque ha sido borrado.
        assertFalse(result.isPresent());
    }

    @Test
    void testFindExistingUsersByFilters() {
        // Sección de Arrange: Obtiene un usuario aleatorio a ser empleado en la prueba
        var testUser = users.values().stream().findAny().orElseThrow();
        // Sección de Act: Se ejecuta la búsqueda aplicando todos los filtros
        Page<User> result = userRepository.findExistingUsersByFilters(testUser.getFullName(), testUser.getEmail(),
                testUser.getDateBirth(), PageRequest.of(0, 10));
        // Sección de Assert: Se verifica que se haya encontrado el usuario que coincide con la búsqueda.
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testFindByStatusNot() {
        // Sección de Arrange: Se asumen los datos precargados como datos de prueba
        // Sección de Act: Se ejecuta la búsqueda de los usuarios que no tienen status DELETED
        List<UserResponse> usersFound = userRepository.findByStatusNot(UserStatus.DELETED);
        // Sección de Assert: Se verifica que se haya encontrado todos usuarios precargados.
        assertFalse(usersFound.isEmpty());
        assertEquals(this.users.size(), usersFound.size());
        assertTrue(this.users.values().stream().map(User::getId).toList().containsAll(usersFound.stream().map(UserResponse::id).toList()));
    }
}