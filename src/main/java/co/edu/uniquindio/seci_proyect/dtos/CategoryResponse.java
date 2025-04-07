package co.edu.uniquindio.seci_proyect.dtos;

import co.edu.uniquindio.seci_proyect.Model.CategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
        private String id;
        private String name;
        private String description;

}