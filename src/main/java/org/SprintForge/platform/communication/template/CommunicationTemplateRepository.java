package org.SprintForge.platform.communication.template;

import org.SprintForge.platform.communication.notification.NotificationChannel;
import org.SprintForge.platform.communication.notification.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository("platformCommunicationTemplateRepository")
public interface CommunicationTemplateRepository extends JpaRepository<CommunicationTemplate, Long> {
    Optional<CommunicationTemplate> findByName(String name);
    Optional<CommunicationTemplate> findByChannelAndTypeAndActiveTrue(NotificationChannel channel, NotificationType type);
}
