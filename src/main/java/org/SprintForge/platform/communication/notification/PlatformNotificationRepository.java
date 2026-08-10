package org.SprintForge.platform.communication.notification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("platformNotificationRepository")
public interface PlatformNotificationRepository extends JpaRepository<PlatformNotification, Long> {

    List<PlatformNotification> findByRecipientIdAndIsReadFalseAndIsArchivedFalseOrderByCreatedAtDesc(String recipientId, Pageable pageable);

    List<PlatformNotification> findByRecipientIdAndIsArchivedFalseOrderByCreatedAtDesc(String recipientId, Pageable pageable);

    List<PlatformNotification> findByWorkspaceIdAndIsArchivedFalseOrderByCreatedAtDesc(String workspaceId, Pageable pageable);

    List<PlatformNotification> findByProjectIdAndIsArchivedFalseOrderByCreatedAtDesc(String projectId, Pageable pageable);

    List<PlatformNotification> findByNotificationTypeOrderByCreatedAtDesc(NotificationType notificationType, Pageable pageable);

    List<PlatformNotification> findByRecipientIdAndIsArchivedTrueOrderByCreatedAtDesc(String recipientId, Pageable pageable);

    long countByRecipientIdAndIsReadFalseAndIsArchivedFalse(String recipientId);

    @Query("SELECT n FROM PlatformNotification n WHERE n.recipientId = :recipientId AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(n.message) LIKE LOWER(CONCAT('%', :query, '%'))) ORDER BY n.createdAt DESC")
    List<PlatformNotification> searchNotifications(@Param("recipientId") String recipientId, @Param("query") String query, Pageable pageable);
}
