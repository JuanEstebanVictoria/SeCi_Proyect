package co.edu.uniquindio.seci_proyect.services;

import co.edu.uniquindio.seci_proyect.dtos.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.UserResponse;
import co.edu.uniquindio.seci_proyect.dtos.UserSearchRequest;
import co.edu.uniquindio.seci_proyect.mappers.UserMapper;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService{
    private UserRepository userRepository;
    private UserMapper userMapper;


    @Override
    public UserResponse createUser(UserRegistrationRequest user) {
        return null;
    }

    @Override
    public Optional<UserResponse> getUser(String id) {
        return Optional.empty();
    }

    @Override
    public Page<UserResponse> searchUsers(UserSearchRequest request) {
        return null;
    }
}
