package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskHistory;
import org.SprintForge.modules.workspace.task.entity.enums.TaskHistoryActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long>, JpaSpecificationExecutor<TaskHistory> {

    List<TaskHistory> findByTaskIdAndIsDeletedFalseOrderByCreatedAtDesc(Long taskId);

    List<TaskHistory> findByPerformedByIdAndIsDeletedFalse(Long userId);

    List<TaskHistory> findByActionTypeAndIsDeletedFalse(TaskHistoryActionType actionType);

    long countByTaskIdAndIsDeletedFalse(Long taskId);

    @Modifying
    @Query("UPDATE TaskHistory th SET th.isDeleted = true, th.updatedAt = CURRENT_TIMESTAMP, th.updatedBy = :actorId WHERE th.task.id = :taskId AND th.isDeleted = false")
    void deleteByTaskId(@Param("taskId") Long taskId, @Param("actorId") String actorId);
}