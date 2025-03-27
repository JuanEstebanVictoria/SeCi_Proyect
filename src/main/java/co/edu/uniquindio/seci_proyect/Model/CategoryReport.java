package co.edu.uniquindio.seci_proyect.Model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "categoriesReport")

public class CategoryReport {
    @Indexed(unique = true)
    private String name;

}
