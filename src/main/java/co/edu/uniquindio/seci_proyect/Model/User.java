package co.edu.uniquindio.seci_proyect.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("usuarios")
public class User {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String fullName;
    private String email;
    private LocalDate dateBirth;
    private String password;
    private Rol rol;
    private UserStatus status;
}