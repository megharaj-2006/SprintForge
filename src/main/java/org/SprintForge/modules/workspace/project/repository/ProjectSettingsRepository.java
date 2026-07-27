package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectSettingsRepository extends JpaRepository<ProjectSettings, Long>, JpaSpecificationExecutor<ProjectSettings> {

    Optional<ProjectSettings> findByProjectId(Long projectId);

    Optional<ProjectSettings> findByProjectIdAndIsDeletedFalse(Long projectId);
}