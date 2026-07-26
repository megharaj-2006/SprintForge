package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectWhiteboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectWhiteboardRepository extends JpaRepository<ProjectWhiteboard, Long>, JpaSpecificationExecutor<ProjectWhiteboard> {
}