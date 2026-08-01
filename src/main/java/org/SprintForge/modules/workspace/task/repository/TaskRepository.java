package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByProjectIdAndIsDeletedFalse(Long projectId);

    Page<Task> findByProjectIdAndIsDeletedFalse(Long projectId, Pageable pageable);

    List<Task> findBySprintIdAndIsDeletedFalse(Long sprintId);

    List<Task> findByProjectIdAndSprintIsNullAndIsDeletedFalseAndArchivedFalse(Long projectId);

    List<Task> findByStatusAndIsDeletedFalse(TaskStatus status);

    List<Task> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, TaskStatus status);

    long countByProjectIdAndStatusAndIsDeletedFalse(Long projectId, TaskStatus status);

    List<Task> findByPriorityAndIsDeletedFalse(TaskPriority priority);

    Optional<Task> findByIdentifierAndIsDeletedFalse(String identifier);

    Optional<Task> findByIdentifier(String identifier);

    boolean existsByIdentifier(String identifier);

    boolean existsByIdentifierAndIsDeletedFalse(String identifier);

    List<Task> findByProjectIdAndArchivedTrueAndIsDeletedFalse(Long projectId);

    long countByProjectIdAndIsDeletedFalse(Long projectId);

    long countByProjectId(Long projectId);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.isDeleted = false AND " +
           "(LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Task> searchTasks(@Param("projectId") Long projectId, @Param("query") String query);

    @Query("SELECT DISTINCT t FROM Task t JOIN TaskAssignment ta ON ta.task.id = t.id " +
           "WHERE t.project.id = :projectId AND t.isDeleted = false AND ta.isDeleted = false")
    List<Task> findAssignedTasks(@Param("projectId") Long projectId);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.isDeleted = false AND " +
           "NOT EXISTS (SELECT ta FROM TaskAssignment ta WHERE ta.task.id = t.id AND ta.isDeleted = false)")
    List<Task> findUnassignedTasks(@Param("projectId") Long projectId);

    List<Task> findByParentTaskIdAndIsDeletedFalse(Long parentTaskId);

    List<Task> findByProjectIdAndParentTaskIsNullAndIsDeletedFalse(Long projectId);

    long countByParentTaskIdAndIsDeletedFalse(Long parentTaskId);

    boolean existsByParentTaskIdAndIsDeletedFalse(Long parentTaskId);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.isDeleted = false " +
           "AND NOT EXISTS (SELECT sub FROM Task sub WHERE sub.parentTask.id = t.id AND sub.isDeleted = false)")
    List<Task> findLeafTasks(@Param("projectId") Long projectId);

    @Query("SELECT t FROM Task t JOIN t.labels l WHERE l.id = :labelId AND t.isDeleted = false AND l.isDeleted = false")
    List<Task> findTasksByLabelId(@Param("labelId") Long labelId);

    List<Task> findByMilestoneIdAndIsDeletedFalse(Long milestoneId);
}