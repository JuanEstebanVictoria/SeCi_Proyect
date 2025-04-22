package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Vote;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    Optional<Vote> findByUserIdAndReportId(ObjectId userId, ObjectId reportId);
    long countByReportId(ObjectId reportId);
    List<Vote> findByUserId(ObjectId userId);
    List<Vote> findByReportId(ObjectId reportId);

    @Query(value = "{ 'reportId': ?0 }", delete = true)
    void deleteAllByReportId(ObjectId reportId);

    boolean existsByUserIdAndReportId(ObjectId userObjId, ObjectId reportObjId);
}