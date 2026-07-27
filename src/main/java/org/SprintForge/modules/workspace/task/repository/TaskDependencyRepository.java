package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long>, JpaSpecificationExecutor<TaskDependency> {

    List<TaskDependency> findByPredecessorTaskIdAndIsDeletedFalse(Long predecessorTaskId);

    List<TaskDependency> findBySuccessorTaskIdAndIsDeletedFalse(Long successorTaskId);

    Optional<TaskDependency> findByPredecessorTaskIdAndSuccessorTaskIdAndIsDeletedFalse(Long predecessorTaskId, Long successorTaskId);

    boolean existsByPredecessorTaskIdAndSuccessorTaskIdAndIsDeletedFalse(Long predecessorTaskId, Long successorTaskId);

    @Query("SELECT COUNT(td) FROM TaskDependency td WHERE (td.predecessorTask.id = :taskId OR td.successorTask.id = :taskId) AND td.isDeleted = false")
    long countDependencies(@Param("taskId") Long taskId);

    @Query("SELECT td.predecessorTask FROM TaskDependency td WHERE td.successorTask.id = :successorTaskId AND td.isDeleted = false")
    List<Task> findBlockingTasks(@Param("successorTaskId") Long successorTaskId);

    @Query("SELECT td.successorTask FROM TaskDependency td WHERE td.predecessorTask.id = :predecessorTaskId AND td.isDeleted = false")
    List<Task> findDependentTasks(@Param("predecessorTaskId") Long predecessorTaskId);

    @Query("SELECT td FROM TaskDependency td WHERE td.successorTask.project.id = :projectId AND td.isDeleted = false")
    List<TaskDependency> findByProjectIdAndIsDeletedFalse(@Param("projectId") Long projectId);

    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE TaskDependency td SET td.isDeleted = true, td.deletedAt = CURRENT_TIMESTAMP " +
           "WHERE td.predecessorTask.id = :predecessorTaskId AND td.successorTask.id = :successorTaskId")
    void deleteByPredecessorTaskIdAndSuccessorTaskId(@Param("predecessorTaskId") Long predecessorTaskId, @Param("successorTaskId") Long successorTaskId);
}