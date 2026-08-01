package org.SprintForge.modules.workspace.comment.repository;

import org.SprintForge.modules.workspace.comment.entity.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long>, JpaSpecificationExecutor<TaskComment> {

    List<TaskComment> findByTaskIdAndIsDeletedFalseOrderByCreatedAtAsc(Long taskId);

    List<TaskComment> findByParentCommentIdAndIsDeletedFalseOrderByCreatedAtAsc(Long parentCommentId);

    List<TaskComment> findByTaskIdAndParentCommentIdIsNullAndIsDeletedFalseOrderByCreatedAtAsc(Long taskId);

    long countByTaskIdAndIsDeletedFalse(Long taskId);

    Optional<TaskComment> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT tc FROM TaskComment tc WHERE tc.taskId = :taskId AND tc.isDeleted = false AND LOWER(tc.content) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY tc.createdAt ASC")
    List<TaskComment> searchComments(@Param("taskId") Long taskId, @Param("query") String query);

    void deleteByTaskId(Long taskId);
}