package co.edu.uniquindio.seci_proyect.Model;

import jakarta.validation.constraints.*;
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
    @NotNull
    @Size(min = 2, max = 80)
    private String fullName;
    @NotBlank
    @Email
    private String email;
    @Past
    private LocalDate dateBirth;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;
    @NotNull
    private Rol rol;
    @NotNull
    private UserStatus status;
}