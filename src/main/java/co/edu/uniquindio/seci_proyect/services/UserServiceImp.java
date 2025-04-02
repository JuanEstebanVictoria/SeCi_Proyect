package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.UserResponse;
import co.edu.uniquindio.seci_proyect.mappers.UserMapper;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;

public class UserServiceImp implements UserService{
    private UserRepository userRepository;
    private UserMapper userMapper;


    @Override
    public UserResponse createUser(UserRegistrationRequest user) {
        return null;
    }
}
