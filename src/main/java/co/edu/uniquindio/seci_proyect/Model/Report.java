package co.edu.uniquindio.seci_proyect.Model;

import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

public class Report {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String description;
    private LocalDateTime date;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    @DBRef
    private List<CategoryReport> categories;
    private ObjectId isUser;
    private ReportStatus status;
    private int ratingsImportant;




}
