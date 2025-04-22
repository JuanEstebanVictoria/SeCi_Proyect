package co.edu.uniquindio.seci_proyect.config;

import co.edu.uniquindio.seci_proyect.Model.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Configuration
public class MongoIndexConfig {

    @Document(collection = "reports")
    @CompoundIndexes({
            @CompoundIndex(name = "user_status_idx", def = "{'userId': 1, 'status': 1}"),
            @CompoundIndex(name = "category_status_idx", def = "{'categories': 1, 'status': 1}"),
            @CompoundIndex(name = "status_votes_idx", def = "{'status': 1, 'votes': -1}")
    })
    public class ReportIndex {}

    @Document(collection = "users")
    @CompoundIndexes({
            @CompoundIndex(name = "email_status_idx", def = "{'email': 1, 'status': 1}"),
            @CompoundIndex(name = "role_status_idx", def = "{'role': 1, 'status': 1}")
    })
    public class UserIndex {}

    @Document(collection = "comments")
    @CompoundIndex(name = "report_date_idx", def = "{'reportId': 1, 'date': -1}")
    public class CommentIndex {}
}