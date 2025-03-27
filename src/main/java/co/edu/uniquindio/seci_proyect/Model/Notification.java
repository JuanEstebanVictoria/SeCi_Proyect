package co.edu.uniquindio.seci_proyect.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    private String id;
    private String message;
    private LocalDateTime date;
    private Boolean read;
}
