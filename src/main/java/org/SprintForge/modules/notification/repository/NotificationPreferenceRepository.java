package org.SprintForge.modules.notification.repository;

import org.SprintForge.modules.notification.entity.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long>, JpaSpecificationExecutor<NotificationPreference> {
}