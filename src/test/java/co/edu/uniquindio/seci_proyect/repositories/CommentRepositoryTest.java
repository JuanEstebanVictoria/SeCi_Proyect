package co.edu.uniquindio.seci_proyect.repositories;


import co.edu.uniquindio.seci_proyect.Model.Comment;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    private Comment testComment;
    private ObjectId testUserId = new ObjectId();
    private ObjectId testReportId = new ObjectId();

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        testComment = new Comment();
        testComment.setUserId(testUserId);
        testComment.setReportId(testReportId);
        testComment.setDate(LocalDateTime.now());
        testComment.setContent("Test comment");
        commentRepository.save(testComment);
    }

    @Test
    void findByUserId_ExistingUserId_ReturnsComments() {
        List<Comment> result = commentRepository.findByUserId(testUserId);
        assertFalse(result.isEmpty());
        assertEquals(testUserId, result.get(0).getUserId());
    }

    @Test
    void findByReportId_ExistingReportId_ReturnsComments() {
        List<Comment> result = commentRepository.findByReportId(testReportId);
        assertFalse(result.isEmpty());
        assertEquals(testReportId, result.get(0).getReportId());
    }

    @Test
    void findByReportIdWithPageable_ReturnsPagedComments() {
        Page<Comment> result = commentRepository.findByReportId(testReportId, PageRequest.of(0, 10));
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void countByReportId_ExistingReportId_ReturnsCount() {
        long count = commentRepository.countByReportId(testReportId);
        assertEquals(1, count);
    }

    @Test
    void findLatestCommentsByReportId_ReturnsSortedComments() {
        List<Comment> result = commentRepository.findLatestCommentsByReportId(
                testReportId,
                PageRequest.of(0, 5)
        );
        assertFalse(result.isEmpty());
    }
}