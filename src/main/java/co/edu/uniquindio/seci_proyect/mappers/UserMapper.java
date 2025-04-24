package co.edu.uniquindio.seci_proyect.mappers;


import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.dtos.user.UserRegistrationRequest;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "REGISTERED")
    @Mapping(target = "password", expression = "java( new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode(userDTO.password()) )")
    User parseOf(UserRegistrationRequest userRegistrationRequest);

    @Mapping(source = "dateBirth", target = "dateBirth", dateFormat = "yyyy-MM-dd")
    UserResponse toUserResponse(User user);
}