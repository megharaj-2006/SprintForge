package org.SprintForge.modules.workspace.attachment.repository;

import org.SprintForge.modules.workspace.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>, JpaSpecificationExecutor<Attachment> {

    List<Attachment> findByTaskIdAndIsDeletedFalseAndArchivedFalse(Long taskId);

    List<Attachment> findByUploadedByAndIsDeletedFalse(Long uploadedBy);

    List<Attachment> findByContentTypeAndIsDeletedFalse(String contentType);

    List<Attachment> findByTaskIdAndArchivedTrueAndIsDeletedFalse(Long taskId);

    long countByTaskIdAndIsDeletedFalseAndArchivedFalse(Long taskId);

    void deleteByTaskId(Long taskId);

    Optional<Attachment> findByIdAndIsDeletedFalse(Long id);

    @Query("SELECT a FROM Attachment a WHERE a.taskId = :taskId AND a.isDeleted = false AND LOWER(a.fileName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Attachment> searchByFileName(@Param("taskId") Long taskId, @Param("query") String query);
}