package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long>, JpaSpecificationExecutor<ChecklistItem> {
    List<ChecklistItem> findByChecklistIdAndIsDeletedFalseOrderByPositionAsc(Long checklistId);
    Optional<ChecklistItem> findByIdAndIsDeletedFalse(Long id);
    List<ChecklistItem> findByChecklistIdAndIsDeletedFalse(Long checklistId);
}