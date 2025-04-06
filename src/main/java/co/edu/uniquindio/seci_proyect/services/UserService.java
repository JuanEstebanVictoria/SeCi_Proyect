package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.UserResponse;
import co.edu.uniquindio.seci_proyect.dtos.UserSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService
{
    UserResponse createUser(UserRegistrationRequest user);

    Optional<UserResponse> getUser(String id);


    Page<UserResponse> searchUsers(UserSearchRequest request);
}
