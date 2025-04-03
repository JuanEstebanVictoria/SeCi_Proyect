package co.edu.uniquindio.seci_proyect.Model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
@Setter


public class Vote {

    private String id;
    private ObjectId userId;
    private ObjectId reportId;
    private LocalDateTime timestamp;
}
