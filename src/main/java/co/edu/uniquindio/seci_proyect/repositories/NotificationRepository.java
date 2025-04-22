package co.edu.uniquindio.seci_proyect.repositories;

import co.edu.uniquindio.seci_proyect.Model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByReceiverId(String receiverId);
    Page<Notification> findByReceiverId(String receiverId, Pageable pageable);
    List<Notification> findByReceiverIdAndReadFalse(String receiverId);
    long countByReceiverIdAndReadFalse(String receiverId);
    List<Notification> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{ 'receiverId': ?0 }", sort = "{ 'date': -1 }")
    List<Notification> findLatestNotificationsByReceiver(String receiverId, int limit);
}