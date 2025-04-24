package co.edu.uniquindio.seci_proyect.controllers.Integration;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.controllers.Integration.utils.LoginUtil;
import co.edu.uniquindio.seci_proyect.data.TestDataLoader;
import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    void testCreateUserSuccess() throws Exception {
        // Sección de Arrange:
        var user = new UserRegistrationRequest("juan@example.com","Juan perez","12345Abc", LocalDate.of(1980,6,25),  Rol.USER,UserStatus.ACTIVE, new GeoJsonPoint(4551.1,6451.1));

        // Sección de Act: Ejecute la acción de invocación del servicio de registro de usuarios
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario registrado.
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value(user.fullName()))
                .andExpect(jsonPath("$.email").value(user.email()))
                .andExpect(jsonPath("$.rol").value(user.rol().toString()));
    }

    @Test
    void testCreateUserValueConflictExceptionWhenEmailExists() throws Exception {
        // Sección de Arrange: Se seleccionan datos de un usuario ya creado para que cuando se envíe la solicitud
        // de creación genere una excepción de tipo ValueConflictException.
        var userStore = users.values().stream().findAny().orElseThrow();
        var user = new UserRegistrationRequest(userStore.getEmail(), "Juan perez","12345Abc",
                LocalDate.of(1980,6,25), Rol.USER, UserStatus.ACTIVE, new GeoJsonPoint(4551.1,6451.1));

        // Sección de Act: Ejecute la acción de invocación del servicio de registro de usuarios
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                // Sección de Assert: Se verifica que el resultado obtenido corresponda a lo esperado un status code de conflicto.
                .andExpect(status().isConflict());
    }

    @Test
    void testGetUserSuccess() throws Exception {
        // Sección de Arrange: Se preparan los datos para enviar una solicitud de un usuario registrado
        var userStore = users.values().stream().findAny().orElseThrow();
        var token = LoginUtil.login(userStore.getEmail(),userStore.getPassword().replace("{noop}",""),mockMvc,objectMapper);
        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        mockMvc.perform(get("/users/"+userStore.getId()).header("Authorization", "Bearer " + token))
                // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario esperado.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(userStore.getFullName()))
                .andExpect(jsonPath("$.email").value(userStore.getEmail()))
                .andExpect(jsonPath("$.rol").value(userStore.getRol().toString()));
    }

    @Test
    void testGetUserNotFound() throws Exception {
        // Sección de Arrange: Se crean los datos del usuario a ser registrado (Con el email de un usuario ya existente).
        var id = UUID.randomUUID().toString();
        var userStore = users.values().stream().filter(user->user.getRol().equals(Rol.ADMIN)).findAny().orElseThrow();
        var token = LoginUtil.login(userStore.getEmail(),userStore.getPassword().replace("{noop}",""),mockMvc,objectMapper);
        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        mockMvc.perform(get("/users/"+id).header("Authorization", "Bearer " + token))
                // Sección de Assert: Se verifica que la respuesta obtenida sea la esperada (404).
                .andExpect(status().isNotFound());
    }
}