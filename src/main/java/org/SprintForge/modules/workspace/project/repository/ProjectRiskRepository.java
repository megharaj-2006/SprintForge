package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRiskRepository extends JpaRepository<ProjectRisk, Long>, JpaSpecificationExecutor<ProjectRisk> {

    long countByProjectIdAndIsDeletedFalse(Long projectId);
}