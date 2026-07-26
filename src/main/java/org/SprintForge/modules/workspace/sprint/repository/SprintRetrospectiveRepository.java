package org.SprintForge.modules.workspace.sprint.repository;

import org.SprintForge.modules.workspace.sprint.entity.SprintRetrospective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRetrospectiveRepository extends JpaRepository<SprintRetrospective, Long>, JpaSpecificationExecutor<SprintRetrospective> {
}