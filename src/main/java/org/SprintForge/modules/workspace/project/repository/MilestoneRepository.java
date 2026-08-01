package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.Milestone;
import org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long>, JpaSpecificationExecutor<Milestone> {

    // --- Core Finders ---

    List<Milestone> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<Milestone> findByProjectIdAndIsArchivedFalseAndIsDeletedFalse(Long projectId);

    List<Milestone> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, MilestoneStatus status);

    boolean existsByProjectIdAndNameAndIsDeletedFalse(Long projectId, String name);

    // --- Overdue Milestones ---

    /**
     * Returns all non-completed, non-deleted milestones whose due date is before the given date.
     */
    @Query("""
            SELECT m FROM Milestone m
            WHERE m.projectId = :projectId
              AND m.isDeleted = false
              AND m.status <> org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus.COMPLETED
              AND m.dueDate < :today
            """)
    List<Milestone> findOverdueMilestones(@Param("projectId") Long projectId,
                                          @Param("today") LocalDate today);

    // --- Progress Queries (against Task table) ---

    /**
     * Counts all non-deleted tasks assigned to the given milestone.
     */
    @Query("""
            SELECT COUNT(t) FROM Task t
            WHERE t.milestoneId = :milestoneId
              AND t.isDeleted = false
            """)
    long countTasksByMilestoneId(@Param("milestoneId") Long milestoneId);

    /**
     * Counts all non-deleted tasks assigned to the given milestone that are DONE.
     */
    @Query("""
            SELECT COUNT(t) FROM Task t
            WHERE t.milestoneId = :milestoneId
              AND t.isDeleted = false
              AND t.status = org.SprintForge.modules.workspace.task.entity.enums.TaskStatus.DONE
            """)
    long countCompletedTasksByMilestoneId(@Param("milestoneId") Long milestoneId);
}
