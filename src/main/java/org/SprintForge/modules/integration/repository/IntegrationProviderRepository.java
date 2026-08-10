package org.SprintForge.modules.integration.repository;

import org.SprintForge.modules.integration.entity.IntegrationProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IntegrationProviderRepository extends JpaRepository<IntegrationProvider, Long> {
    Optional<IntegrationProvider> findByProviderKey(String providerKey);
}
