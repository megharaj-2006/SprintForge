package org.SprintForge.modules.ai.repository;

import org.SprintForge.modules.ai.entity.AIProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIProviderRepository extends JpaRepository<AIProvider, Long> {
    Optional<AIProvider> findByProviderKey(String providerKey);
}
