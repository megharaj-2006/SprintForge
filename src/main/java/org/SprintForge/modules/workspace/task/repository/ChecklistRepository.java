package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Long>, JpaSpecificationExecutor<Checklist> {
    List<Checklist> findByTaskIdAndIsDeletedFalseOrderByPositionAsc(Long taskId);
    Optional<Checklist> findByIdAndIsDeletedFalse(Long id);
}