package org.SprintForge.modules.workspace.goal.repository;

import org.SprintForge.modules.workspace.goal.entity.KeyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyResultRepository extends JpaRepository<KeyResult, Long>, JpaSpecificationExecutor<KeyResult> {
}