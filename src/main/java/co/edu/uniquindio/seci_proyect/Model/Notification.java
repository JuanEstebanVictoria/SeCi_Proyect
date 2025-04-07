package co.edu.uniquindio.seci_proyect.Model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document( collection ="notifications")

public class Notification {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @NotBlank
    private String message;
    @Past
    private LocalDateTime date;
    @NotNull
    private Boolean read;
    private  String receiverId;
}
