package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskWatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskWatcherRepository extends JpaRepository<TaskWatcher, Long>, JpaSpecificationExecutor<TaskWatcher> {

    List<TaskWatcher> findByTaskIdAndIsDeletedFalse(Long taskId);

    List<TaskWatcher> findByUserIdAndIsDeletedFalse(Long userId);

    boolean existsByTaskIdAndUserIdAndIsDeletedFalse(Long taskId, Long userId);

    long countByTaskIdAndIsDeletedFalse(Long taskId);

    Optional<TaskWatcher> findByTaskIdAndUserIdAndIsDeletedFalse(Long taskId, Long userId);

    void deleteByTaskId(Long taskId);

    @Query("SELECT t FROM Task t WHERE t.id IN (SELECT tw.taskId FROM TaskWatcher tw WHERE tw.userId = :userId AND tw.isDeleted = false) AND t.isDeleted = false")
    List<Task> findWatchingTasks(@Param("userId") Long userId);

    Optional<TaskWatcher> findByIdAndIsDeletedFalse(Long id);
}