package org.SprintForge.modules.workspace.notification.repository;

import org.SprintForge.modules.workspace.notification.entity.TaskNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskNotificationRepository extends JpaRepository<TaskNotification, Long> {

    List<TaskNotification> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);

    Page<TaskNotification> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId, Pageable pageable);

    List<TaskNotification> findByUserIdAndIsReadFalseAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);

    long countByUserIdAndIsReadFalseAndIsDeletedFalse(Long userId);
}