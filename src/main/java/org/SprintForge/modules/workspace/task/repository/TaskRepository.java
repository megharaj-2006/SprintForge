package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    Optional<Task> findByProjectIdAndTaskNumber(Long projectId, String taskNumber);

    List<Task> findByProjectIdAndIsDeletedFalse(Long projectId);

    Page<Task> findByProjectIdAndIsDeletedFalse(Long projectId, Pageable pageable);

    List<Task> findBySprintIdAndIsDeletedFalse(Long sprintId);

    List<Task> findByEpicIdAndIsDeletedFalse(Long epicId);

    List<Task> findByAssigneeIdAndIsDeletedFalse(Long assigneeId);

    Page<Task> findByAssigneeIdAndIsDeletedFalse(Long assigneeId, Pageable pageable);

    List<Task> findByParentTaskIdAndIsDeletedFalse(Long parentTaskId);

    long countByProjectIdAndStatusId(Long projectId, Long statusId);

    long countBySprintIdAndIsDeletedFalse(Long sprintId);
}