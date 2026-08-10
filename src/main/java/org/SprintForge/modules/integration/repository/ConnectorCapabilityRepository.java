package org.SprintForge.modules.integration.repository;

import org.SprintForge.modules.integration.entity.ConnectorCapability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectorCapabilityRepository extends JpaRepository<ConnectorCapability, Long> {
    Optional<ConnectorCapability> findByCapabilityKey(String capabilityKey);
}
