package co.edu.uniquindio.seci_proyect.Model;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "users")
@CompoundIndex(name = "email_status_idx", def = "{'email': 1, 'status': 1}")
public class User {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String email;
    private String fullName;
    private String password;
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate dateBirth;
    private Rol rol;
    private UserStatus status;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    // Campos para activación
    private String activationCode;
    private LocalDateTime activationCodeExpiry;

    // Campos para recuperación de contraseña
    private String resetPasswordCode;
    private LocalDateTime resetPasswordCodeExpiry;

    // Métodos específicos para manejar códigos
    public void generateAndSetActivationCode() {
        this.activationCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.activationCodeExpiry = LocalDateTime.now().plusMinutes(15);
    }

    public void generateAndSetResetPasswordCode() {
        this.resetPasswordCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.resetPasswordCodeExpiry = LocalDateTime.now().plusMinutes(15);
    }

    public boolean isActivationCodeValid() {
        return this.activationCode != null &&
                this.activationCodeExpiry != null &&
                this.activationCodeExpiry.isAfter(LocalDateTime.now());
    }

    public boolean isResetPasswordCodeValid() {
        return this.resetPasswordCode != null &&
                this.resetPasswordCodeExpiry != null &&
                this.resetPasswordCodeExpiry.isAfter(LocalDateTime.now());
    }

    public Collection<Object> getRoles() {
        return null;
    }
}