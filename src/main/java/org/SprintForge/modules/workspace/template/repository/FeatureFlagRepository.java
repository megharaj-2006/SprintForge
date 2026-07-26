package org.SprintForge.modules.workspace.template.repository;

import org.SprintForge.modules.workspace.template.entity.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long>, JpaSpecificationExecutor<FeatureFlag> {
}