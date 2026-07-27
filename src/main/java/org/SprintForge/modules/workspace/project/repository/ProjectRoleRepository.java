package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long>, JpaSpecificationExecutor<ProjectRole> {

    Optional<ProjectRole> findByProjectIdAndNameAndIsDeletedFalse(Long projectId, String name);
}
