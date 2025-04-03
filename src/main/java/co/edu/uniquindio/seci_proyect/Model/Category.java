package co.edu.uniquindio.seci_proyect.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "categories")
public class Category {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
    private CategoryStatus status;
}
