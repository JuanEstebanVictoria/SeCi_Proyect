package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.UserResponse;

public interface UserService
{
    UserResponse createUser(UserRegistrationRequest user);

}
