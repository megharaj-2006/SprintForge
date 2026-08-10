package org.SprintForge.platform.featureflag.repository;

import org.SprintForge.platform.featureflag.domain.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("platformFeatureFlagRepository")
public interface PlatformFeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {
    Optional<FeatureFlag> findByName(String name);
}
