package co.edu.uniquindio.seci_proyect.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "reports")
@CompoundIndexes({
        @CompoundIndex(name = "status_createdAt_idx", def = "{'status': 1, 'createdAt': -1}"),
        @CompoundIndex(name = "user_status_idx", def = "{'userId': 1, 'status': 1}")
})
public class Report {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    @NotBlank
    private String title;
    @DBRef
    private List<Category> categories;
    @NotBlank
    private String description;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    private List<String> urlsImages;
    @NotNull
    private ReportStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Vote> votes;
    private ObjectId userId;
    private List<Comment> comments;
}
