package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskLabel;
import org.SprintForge.modules.workspace.task.entity.TaskLabelMapping;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long>, JpaSpecificationExecutor<TaskLabel> {

    List<TaskLabel> findByProject(Project project);

    List<TaskLabel> findByName(String name);

    List<TaskLabel> findByColor(String color);

    boolean existsByProjectAndName(Project project, String name);

    List<TaskLabel> findByArchivedTrue();

    List<TaskLabel> findByProjectId(Long projectId);

    @Query("SELECT t FROM TaskLabel t WHERE (:keyword IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:projectId IS NULL OR t.project.id = :projectId)")
    List<TaskLabel> searchLabels(@Param("keyword") String keyword, @Param("projectId") Long projectId);

    @Query("SELECT COUNT(DISTINCT tlm.taskId) FROM TaskLabelMapping tlm WHERE tlm.labelId = :labelId AND tlm.isDeleted = false")
    long countTasksUsingLabel(@Param("labelId") Long labelId);
}