package org.SprintForge.modules.workspace.timelog.repository;

import org.SprintForge.modules.workspace.timelog.entity.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long>, JpaSpecificationExecutor<TimeEntry> {

    List<TimeEntry> findByTaskIdAndIsDeletedFalseOrderByStartTimeDesc(Long taskId);

    List<TimeEntry> findByUserIdAndIsDeletedFalseOrderByStartTimeDesc(Long userId);

    List<TimeEntry> findByTaskIdAndUserIdAndIsDeletedFalseOrderByStartTimeDesc(Long taskId, Long userId);

    List<TimeEntry> findByUserIdAndStartTimeBetweenAndIsDeletedFalse(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<TimeEntry> findByUserIdAndEndTimeIsNullAndIsDeletedFalse(Long userId);

    @Query("SELECT COALESCE(SUM(te.durationMinutes), 0) FROM TimeEntry te WHERE te.taskId = :taskId AND te.isDeleted = false")
    Long getTotalTimeForTask(@Param("taskId") Long taskId);

    @Query("SELECT COALESCE(SUM(te.durationMinutes), 0) FROM TimeEntry te WHERE te.taskId IN (SELECT t.id FROM Task t WHERE t.project.id = :projectId) AND te.isDeleted = false")
    Long getTotalTimeForProject(@Param("projectId") Long projectId);

    @Query("SELECT COALESCE(SUM(te.durationMinutes), 0) FROM TimeEntry te WHERE te.userId = :userId AND te.isDeleted = false")
    Long getTotalTimeForUser(@Param("userId") Long userId);

    void deleteByTaskId(Long taskId);

    Optional<TimeEntry> findByIdAndIsDeletedFalse(Long id);
}