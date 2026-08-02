package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long>, JpaSpecificationExecutor<ProjectTag> {

    java.util.List<ProjectTag> findByProjectIdAndIsDeletedFalse(Long projectId);

    boolean existsByProjectIdAndNameAndIsDeletedFalse(Long projectId, String name);
}