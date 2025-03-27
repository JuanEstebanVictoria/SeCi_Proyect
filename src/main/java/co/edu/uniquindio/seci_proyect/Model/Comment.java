package co.edu.uniquindio.seci_proyect.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Comment {

    private String content;
    private LocalDateTime date;
    private ObjectId idUser;
    private ObjectId idReport;
}
