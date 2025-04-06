package co.edu.uniquindio.seci_proyect.Model;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Comment {

    private ObjectId userId;
    private ObjectId reportId;
    private String content;
    private LocalDateTime date;

}
