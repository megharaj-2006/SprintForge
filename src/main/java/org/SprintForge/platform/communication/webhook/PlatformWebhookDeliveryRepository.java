package org.SprintForge.platform.communication.webhook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("platformWebhookDeliveryRepository")
public interface PlatformWebhookDeliveryRepository extends JpaRepository<PlatformWebhookDelivery, Long> {
    List<PlatformWebhookDelivery> findByStatus(String status);
}
