package org.SprintForge.modules.workspace.epic.repository;

import org.SprintForge.modules.workspace.epic.entity.Epic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpicRepository extends JpaRepository<Epic, Long>, JpaSpecificationExecutor<Epic> {

    List<Epic> findByProjectIdAndIsDeletedFalse(Long projectId);
}