package org.SprintForge.modules.workspace.notification.repository;

import org.SprintForge.modules.workspace.notification.entity.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {

    Optional<NotificationPreference> findByUserIdAndIsDeletedFalse(Long userId);
}
