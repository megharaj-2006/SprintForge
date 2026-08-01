package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.RecurringTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecurringTaskRepository extends JpaRepository<RecurringTask, Long>, JpaSpecificationExecutor<RecurringTask> {

    Optional<RecurringTask> findByTaskIdAndIsDeletedFalse(Long taskId);

    List<RecurringTask> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    List<RecurringTask> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<RecurringTask> findByEnabledTrueAndPausedFalseAndIsDeletedFalse();

    @Query("SELECT r FROM RecurringTask r WHERE r.enabled = true AND r.paused = false AND r.isDeleted = false AND r.nextExecution <= :now")
    List<RecurringTask> findDueRecurringTasks(@Param("now") LocalDateTime now);

    @Query("SELECT r FROM RecurringTask r WHERE r.paused = true AND r.isDeleted = false")
    List<RecurringTask> findPausedTasks();

    @Query("SELECT r FROM RecurringTask r WHERE r.isDeleted = false AND ((r.endDate IS NOT NULL AND r.endDate < CURRENT_DATE) OR (r.maxOccurrences IS NOT NULL AND r.generatedOccurrences >= r.maxOccurrences))")
    List<RecurringTask> findExpiredTasks();
}