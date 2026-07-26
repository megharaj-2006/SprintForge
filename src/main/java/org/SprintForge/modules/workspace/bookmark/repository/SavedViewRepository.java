package org.SprintForge.modules.workspace.bookmark.repository;

import org.SprintForge.modules.workspace.bookmark.entity.SavedView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedViewRepository extends JpaRepository<SavedView, Long>, JpaSpecificationExecutor<SavedView> {
}