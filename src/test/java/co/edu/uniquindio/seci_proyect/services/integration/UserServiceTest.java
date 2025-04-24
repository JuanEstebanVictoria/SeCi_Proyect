package co.edu.uniquindio.seci_proyect.services.integration;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.data.TestDataLoader;
import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

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
    void testCreateUser() {
        // Sección de Arrange: Se crean los datos del usuario a ser registrado
        var user = new UserRegistrationRequest("juan@example.com","Juan Perez","12345Abc",
                LocalDate.of(1980,6,25), Rol.USER, UserStatus.ACTIVE, new GeoJsonPoint(4551.1,6451.1));
        // Sección de Act: Ejecute la acción de crear usuario.
        var newUser = userService.createUser(user);
        // Sección de Assert: Se verifica que el usuario se haya registrado con los datos proporcionados.
        assertNotNull(newUser.id());
        assertEquals(user.email(),newUser.email());
        assertEquals(user.fullName(),newUser.fullName());
        assertEquals(user.dateBirth(),newUser.dateBirth());
        assertEquals(user.rol(),newUser.rol());
    }

    @Test
    void testCreateUserThrowsValueConflictExceptionWhenEmailExists() {
        // Sección de Arrange: Se crean los datos del usuario a ser registrado (Con el email de un usuario ya existente).
        var userStore = users.values().stream().findAny().orElseThrow();
        var user = new UserRegistrationRequest(userStore.getEmail(), "Juan Perez","12345Abc",
                LocalDate.of(1980,6,25), Rol.USER, UserStatus.ACTIVE, new GeoJsonPoint(4551.1,6451.1));
        // Sección de Act y Sección de Assert: Ejecute la acción de crear usuario se verifica que genere una excepción debido al email repetido.
        assertThrows(ValueConflictException.class,() -> userService.createUser(user) );
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testGetUserSuccess() {
        // Sección de Arrange: Se obtiene aleatoriamente uno de los usuarios registrado para pruebas.
        var userStore = users.values().stream().findAny().orElseThrow();
        // Sección de Act: Ejecute la acción de obtener usuario basado en su Id.
        var foundUser = userService.getUser(userStore.getId()).orElseThrow();
        // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario almacenado.
        assertEquals(userStore.getFullName(),foundUser.fullName());
        assertEquals(userStore.getDateBirth(),foundUser.dateBirth());
        assertEquals(userStore.getRol(),foundUser.rol());
    }

    @Test
    @WithMockUser(username = "ana@example.com", authorities = {"USER"})
    void testGetUserWithSameUserSuccess() {
        // Sección de Arrange: Se obtiene aleatoriamente uno de los usuarios registrado para pruebas.
        var userStore = users.values().stream().filter(user->user.getEmail().equalsIgnoreCase("ana@example.com")).findAny().orElseThrow();
        // Sección de Act: Ejecute la acción de obtener usuario basado en su Id.
        var foundUser = userService.getUser(userStore.getId()).orElseThrow();
        // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario almacenado.
        assertEquals(userStore.getFullName(),foundUser.fullName());
        assertEquals(userStore.getDateBirth(),foundUser.dateBirth());
        assertEquals(userStore.getRol(),foundUser.rol());
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void testGetUserWithDiferentUserSuccess() {
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe retornar la respuesta dada.
        var userStore = users.values().stream().findAny().orElseThrow();

        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        assertThrows(AuthorizationDeniedException.class,()->userService.getUser(userStore.getId()));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testGetUserNotFound() {
        // Sección de Arrange: Se crean los datos del usuario a ser registrado (Con el email de un usuario ya existente).
        var id = UUID.randomUUID().toString();
        // Sección de Act: Ejecute la acción de obtener usuario basado en su Id.
        var user = userService.getUser(id);
        // Sección de Assert: Se verifica que los datos obtenidos correspondan a lo esperado.
        assertEquals(Optional.empty(),user);
    }
}