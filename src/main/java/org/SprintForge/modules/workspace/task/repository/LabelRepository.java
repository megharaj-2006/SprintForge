package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long>, JpaSpecificationExecutor<Label> {

    List<Label> findByProjectIdAndIsDeletedFalse(Long projectId);

    Optional<Label> findByNameAndProjectIdAndIsDeletedFalse(String name, Long projectId);

    List<Label> findByProjectIdAndColorAndIsDeletedFalse(Long projectId, String color);

    boolean existsByProjectIdAndNameAndIsDeletedFalse(Long projectId, String name);

    List<Label> findByProjectIdAndArchivedTrueAndIsDeletedFalse(Long projectId);

    @Query("SELECT l FROM Label l WHERE l.project.id = :projectId AND l.isDeleted = false " +
           "AND LOWER(l.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Label> searchLabels(@Param("projectId") Long projectId, @Param("query") String query);

    @Query("SELECT COUNT(t) FROM Task t JOIN t.labels l WHERE l.id = :labelId AND t.isDeleted = false AND l.isDeleted = false")
    long countTasksUsingLabel(@Param("labelId") Long labelId);
}
