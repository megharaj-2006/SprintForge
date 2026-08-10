package org.SprintForge.modules.management.repository;

import org.SprintForge.modules.management.entity.FeatureFlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("mgmtFeatureFlagRepository")
public interface MgmtFeatureFlagRepository extends JpaRepository<FeatureFlagEntity, Long> {
    Optional<FeatureFlagEntity> findByFlagKey(String flagKey);
}
