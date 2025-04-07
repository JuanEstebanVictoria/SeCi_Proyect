package co.edu.uniquindio.seci_proyect.Model;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class Vote {
    @EqualsAndHashCode.Include
    private String id;
    private ObjectId userId;
    private ObjectId reportId;
    private LocalDateTime timestamp;
}
