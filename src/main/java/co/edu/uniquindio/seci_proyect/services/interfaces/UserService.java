package co.edu.uniquindio.seci_proyect.services.interfaces;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.dtos.user.*;
import co.edu.uniquindio.seci_proyect.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface UserService {
    UserResponse createUser(UserRegistrationRequest user);
    Optional<UserResponse> getUser(String id);
    Page<UserResponse> searchUsers(UserSearchRequest request);

    void activateUser(String activationCode);
    UserResponse updateUser(String userId, UserUpdateRequest request);
    void requestPasswordReset(String email);
    void resetPassword(PasswordResetRequest request);
    void deactivateUser(String userId);

    UserResponse getUserById(String id) throws Exception;
    List<UserResponse> getAllUsers();
    String deleteUser(String id) throws Exception;
    String activateAccount(String activationCode) throws Exception;
    UserResponse getUserByEmail(String email) throws ResourceNotFoundException;
    Collection<? extends GrantedAuthority> getAuthorities(Rol rol);
}
