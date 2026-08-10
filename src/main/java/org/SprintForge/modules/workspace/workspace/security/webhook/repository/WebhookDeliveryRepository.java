package org.SprintForge.modules.workspace.workspace.security.webhook.repository;

import org.SprintForge.modules.workspace.workspace.security.webhook.entity.WebhookDelivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WebhookDeliveryRepository extends JpaRepository<WebhookDelivery, Long>, JpaSpecificationExecutor<WebhookDelivery> {
    List<WebhookDelivery> findByWebhookIdOrderByCreatedAtDesc(Long webhookId, Pageable pageable);
    long countByWebhookIdAndIsSuccessFalse(Long webhookId);
    List<WebhookDelivery> findByWorkspaceIdOrderByCreatedAtDesc(Long workspaceId, Pageable pageable);
}
