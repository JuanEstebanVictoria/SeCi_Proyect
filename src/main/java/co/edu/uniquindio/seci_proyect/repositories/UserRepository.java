package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Rol;
import co.edu.uniquindio.seci_proyect.Model.User;
import co.edu.uniquindio.seci_proyect.Model.UserStatus;
import co.edu.uniquindio.seci_proyect.dtos.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import co.edu.uniquindio.seci_proyect.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Consultas básicas
    List<User> findByRol(Rol rol);
    List<User> findByStatus(UserStatus status);
    Optional<User> findUserByEmail(String email);
    List<UserResponse> findByStatusNot(UserStatus status);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User> findByActivationCode(String activationCode);
    Optional<User> findByResetPasswordCode(String resetPasswordCode);
   // Optional<User> findUserByEmailNot(String email);

    @Query(value =  "{ 'status': { $ne: 'DELETED' }, " +
            "  'fullName': { $regex: ?0, $options: 'i' }, " +
            "  'email': { $regex: ?1, $options: 'i' }, " +
            "  ?#{ [2] != null ? 'dateBirth' : '_ignore' } : ?2 }",
            sort = "{ 'fullName': 1 }" )
    Page<User> findExistingUsersByFilters(String fullName, String email, LocalDate dateBirth, Pageable pageable);

    // Consulta geoespacial para encontrar usuarios cercanos
    @Query("{'location': {$nearSphere: {$geometry: {type: 'Point', coordinates: [?0, ?1]}, $maxDistance: ?2}}}")
    List<User> findUsersNearLocation(double longitude, double latitude, double maxDistanceInMeters);

    // Consulta para usuarios con reportes verificados
    @Query(value = "{'status': 'ACTIVE'}", fields = "{'email': 1, 'fullName': 1}")
    List<User> findActiveUsersWithProjection();


}
