package co.edu.uniquindio.seci_proyect.services.impl;

import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import co.edu.uniquindio.seci_proyect.dtos.user.UserSearchRequest;
import co.edu.uniquindio.seci_proyect.exceptions.ValueConflictException;
import co.edu.uniquindio.seci_proyect.mappers.UserMapper;
import co.edu.uniquindio.seci_proyect.repositories.UserRepository;
import co.edu.uniquindio.seci_proyect.services.interfaces.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;


    @Override
    public UserResponse createUser(UserRegistrationRequest user) {
        if(userRepository.findUserByEmail(user.email()).isPresent()){
            throw new ValueConflictException("Email already registered");
        }
        var newUser = userMapper.parseOf(user);
        return userMapper.toUserResponse(userRepository.save(newUser));
    }

    @Override
    public Optional<UserResponse> getUser(String id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse);
    }

    @Override
    public Page<UserResponse> searchUsers(UserSearchRequest request) {
        // configurar paginacion
        Pageable pageable = PageRequest.of(request.page(), request.size());

        // llamar al repositorio con filtros
        return userRepository.findExistingUsersByFilters(
                request.fullName(),
                request.email(),
                request.dateBirth(),
                pageable
        ).map(userMapper::toUserResponse);
    }
}
