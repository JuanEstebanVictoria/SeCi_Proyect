package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByUserId(ObjectId userId);
    List<Comment> findByReportId(ObjectId reportId);
    Page<Comment> findByReportId(ObjectId reportId, Pageable pageable);
    List<Comment> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    long countByReportId(ObjectId reportId);
    long countByUserId(ObjectId userId);

    @Query(value = "{ 'reportId': ?0 }", sort = "{ 'date': -1 }")
    List<Comment> findLatestCommentsByReportId(ObjectId reportId, Pageable pageable);
}