package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceFavoriteRepository extends JpaRepository<WorkspaceFavorite, Long>, JpaSpecificationExecutor<WorkspaceFavorite> {
}