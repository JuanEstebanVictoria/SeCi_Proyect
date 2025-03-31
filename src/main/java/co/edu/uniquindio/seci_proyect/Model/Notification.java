package co.edu.uniquindio.seci_proyect.Model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    @Id
    private String id;
    @NotBlank
    private String message;
    @Past
    private LocalDateTime date;
    @NotNull
    private Boolean read;
}
