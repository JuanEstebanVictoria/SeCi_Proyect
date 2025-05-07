package co.edu.uniquindio.seci_proyect.repositories;


import co.edu.uniquindio.seci_proyect.Model.Vote;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    private Vote testVote;
    private ObjectId testUserId = new ObjectId();
    private ObjectId testReportId = new ObjectId();

    @BeforeEach
    void setUp() {
        voteRepository.deleteAll();
        testVote = new Vote();
        testVote.setUserId(testUserId);
        testVote.setReportId(testReportId);
        voteRepository.save(testVote);
    }

    @Test
    void findByUserIdAndReportId_ExistingIds_ReturnsVote() {
        Optional<Vote> result = voteRepository.findByUserIdAndReportId(testUserId, testReportId);
        assertTrue(result.isPresent());
        assertEquals(testUserId, result.get().getUserId());
        assertEquals(testReportId, result.get().getReportId());
    }

    @Test
    void countByReportId_ExistingReportId_ReturnsCount() {
        long count = voteRepository.countByReportId(testReportId);
        assertEquals(1, count);
    }

    @Test
    void existsByUserIdAndReportId_ExistingIds_ReturnsTrue() {
        boolean exists = voteRepository.existsByUserIdAndReportId(testUserId, testReportId);
        assertTrue(exists);
    }

    @Test
    void deleteAllByReportId_ExistingReportId_DeletesVotes() {
        voteRepository.deleteAllByReportId(testReportId);
        long count = voteRepository.countByReportId(testReportId);
        assertEquals(0, count);
    }
}