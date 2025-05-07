package co.edu.uniquindio.seci_proyect.controllers.Unit;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import co.edu.uniquindio.seci_proyect.dtos.user.UserUpdateRequest;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.services.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.MediaType;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;

    private UserRegistrationRequest user;
    private UserResponse userResponse;

    @BeforeEach
    void setup() {
        user = new UserRegistrationRequest("juan@example.com","Juan Perez","12345Abc", LocalDate.of(1980,6,25), Rol.USER, UserStatus.ACTIVE, new GeoJsonPoint(4551.1,6451.1));
        userResponse = new UserResponse(UUID.randomUUID().toString(),user.email(), user.fullName(), user.dateBirth(),user.rol(), user.location());
    }

    @Test
    void testCreateUserSuccess() throws Exception {
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe retornar la respuesta dada.
        when(userService.createUser(any(UserRegistrationRequest.class))).thenReturn(userResponse);
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
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe generar una excepción de tipo ValueConflictException.
        when(userService.createUser(any(UserRegistrationRequest.class))).thenThrow(ValueConflictException.class);

        // Sección de Act: Ejecute la acción de invocación del servicio de registro de usuarios
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                // Sección de Assert: Se verifica que el resultado obtenido corresponda a lo esperado un status code de conflicto.
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testGetUserSuccess() throws Exception {
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe retornar la respuesta dada.
        when(userService.getUser(userResponse.id())).thenReturn(Optional.of(userResponse));
        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        mockMvc.perform(get("/users/"+userResponse.id()))
                // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario esperado.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(user.fullName()))
                .andExpect(jsonPath("$.email").value(user.email()))
                .andExpect(jsonPath("$.rol").value(user.rol().toString()));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void testGetUserWithSameUserSuccess() throws Exception {
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe retornar la respuesta dada.
        when(userService.getUser(userResponse.id())).thenReturn(Optional.of(userResponse));

        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        mockMvc.perform(get("/users/"+userResponse.id()))
                // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario esperado.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(user.fullName()))
                .andExpect(jsonPath("$.email").value(user.email()))
                .andExpect(jsonPath("$.rol").value(user.rol().toString()));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void testGetUserWithDiferentUserSuccess() throws Exception {
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe retornar la respuesta dada.
        when(userService.getUser(userResponse.id())).thenThrow(AuthorizationDeniedException.class);

        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        mockMvc.perform(get("/users/"+userResponse.id()))
                // Sección de Assert: Se verifica que los datos obtenidos correspondan a los del usuario esperado.
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testGetUserNotFound() throws Exception {
        // Sección de Arrange: Se configura la respuesta simulada por el componente userService,
        // en este cado se indica que cuando se envíe la solicitud de creación debe retornar la respuesta dada.
        when(userService.getUser(userResponse.id())).thenReturn(Optional.empty());
        // Sección de Act: Ejecute la acción de invocación del servicio de consulta de usuarios
        mockMvc.perform(get("/users/"+userResponse.id()))
                // Sección de Assert: Se verifica que la respuesta obtenida sea la esperada (404).
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testSearchUsersSuccess() throws Exception {
        // Configurar mock
        Page<UserResponse> mockPage = new PageImpl<>(List.of(userResponse), PageRequest.of(0, 10), 1);
        when(userService.searchUsers(any())).thenReturn(mockPage);

        // Ejecutar prueba
        mockMvc.perform(get("/users")
                        .param("fullName", "Juan")
                        .param("email", "example.com")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fullName").value(user.fullName()))
                .andExpect(jsonPath("$.content[0].email").value(user.email()))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @WithMockUser(authorities = {"USER"})
    void testSearchUsersUnauthorizedForNonAdmin() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "juan@example.com", authorities = {"USER"})
    void testUpdateUserSuccess() throws Exception {
        // Configurar datos de prueba
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "Juan Perez Updated",
                new GeoJsonPoint(4551.2, 6451.2),
                LocalDate.of(1980, 6, 25)
        );

        UserResponse updatedResponse = new UserResponse(
                userResponse.id(),
                userResponse.email(),
                updateRequest.fullName(),
                updateRequest.dateBirth(),
                userResponse.rol(),
                updateRequest.location()
        );

        // Configurar mock
        when(userService.updateUser(eq(userResponse.id()), any(UserUpdateRequest.class)))
                .thenReturn(updatedResponse);

        // Ejecutar prueba
        mockMvc.perform((org.springframework.test.web.servlet.RequestBuilder) put("/users/{id}", userResponse.id())
                        .contentType(MediaType.valueOf("application/json"))
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(updateRequest))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(updateRequest.fullName()));
    }

    @Test
    @WithMockUser(username = "other@example.com", authorities = {"USER"})
    void testUpdateUserUnauthorizedForOtherUser() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "Juan Perez Updated",
                new GeoJsonPoint(4551.2, 6451.2),
                LocalDate.of(1980, 6, 25)
        );

        when(userService.updateUser(eq(userResponse.id()), any(UserUpdateRequest.class)))
                .thenThrow(AuthorizationDeniedException.class);

        mockMvc.perform((org.springframework.test.web.servlet.RequestBuilder) put("/users/{id}", userResponse.id())
                        .contentType(MediaType.valueOf("application/json"))
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(updateRequest))))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(username = "juan@example.com", authorities = {"USER"})
    void testDeactivateUserSuccess() throws Exception {
        doNothing().when(userService).deactivateUser(userResponse.id());

        mockMvc.perform(delete("/users/{id}", userResponse.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "other@example.com", authorities = {"USER"})
    void testDeactivateUserUnauthorizedForOtherUser() throws Exception {
        doThrow(AuthorizationDeniedException.class)
                .when(userService).deactivateUser(userResponse.id());

        mockMvc.perform(delete("/users/{id}", userResponse.id()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void testDeactivateUserSuccessByAdmin() throws Exception {
        doNothing().when(userService).deactivateUser(userResponse.id());

        mockMvc.perform(delete("/users/{id}", userResponse.id()))
                .andExpect(status().isNoContent());
    }
}