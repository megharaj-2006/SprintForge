package org.SprintForge.modules.workflow.marketplace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketplaceTemplateRepository extends JpaRepository<MarketplaceTemplate, Long> {
    Optional<MarketplaceTemplate> findByTemplateKey(String templateKey);
    List<MarketplaceTemplate> findByCategoryAndPublicVisibleTrue(String category);
    List<MarketplaceTemplate> findByPublicVisibleTrue();
}
