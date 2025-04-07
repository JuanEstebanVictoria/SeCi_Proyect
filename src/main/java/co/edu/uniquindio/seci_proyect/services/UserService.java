package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import co.edu.uniquindio.seci_proyect.dtos.user.UserSearchRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface UserService
{
    UserResponse createUser(UserRegistrationRequest user);

    Optional<UserResponse> getUser(String id);


    Page<UserResponse> searchUsers(UserSearchRequest request);
}
