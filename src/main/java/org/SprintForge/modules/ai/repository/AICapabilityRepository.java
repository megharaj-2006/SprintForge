package org.SprintForge.modules.ai.repository;

import org.SprintForge.modules.ai.entity.AICapability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AICapabilityRepository extends JpaRepository<AICapability, Long> {
    Optional<AICapability> findByCapabilityKey(String capabilityKey);
}
