package co.edu.uniquindio.seci_proyect.data;


import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestDataLoaderTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private TestDataLoader testDataLoader;

    @Test
    void loadTestData_shouldReturnUserMap() {
        // Configurar mocks
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar método
        Map<String, User> result = TestDataLoader.loadTestData(userRepository, mongoTemplate);

        // Verificar resultados
        assertNotNull(result);
        assertFalse(result.isEmpty());

        // Verificar que se llamó a save por cada usuario
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void loadTestData_shouldContainAdminUser() {
        // Configurar mocks
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar método
        Map<String, User> result = TestDataLoader.loadTestData(userRepository, mongoTemplate);

        // Verificar que existe al menos un admin
        boolean hasAdmin = result.values().stream()
                .anyMatch(user -> user.getRol().equals(Rol.ADMIN));
        assertTrue(hasAdmin);
    }

    @Test
    void loadTestData_shouldContainRegularUsers() {
        // Configurar mocks
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Ejecutar método
        Map<String, User> result = TestDataLoader.loadTestData(userRepository, mongoTemplate);

        // Verificar que existen usuarios regulares
        boolean hasRegularUsers = result.values().stream()
                .anyMatch(user -> user.getRol().equals(Rol.USER));
        assertTrue(hasRegularUsers);
    }
}