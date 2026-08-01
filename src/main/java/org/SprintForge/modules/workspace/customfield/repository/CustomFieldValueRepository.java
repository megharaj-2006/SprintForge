package org.SprintForge.modules.workspace.customfield.repository;

import org.SprintForge.modules.workspace.customfield.entity.CustomFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomFieldValueRepository extends JpaRepository<CustomFieldValue, Long>, JpaSpecificationExecutor<CustomFieldValue> {

    List<CustomFieldValue> findByTaskIdAndIsDeletedFalse(Long taskId);

    List<CustomFieldValue> findByCustomFieldIdAndIsDeletedFalse(Long customFieldId);

    Optional<CustomFieldValue> findByTaskIdAndCustomFieldIdAndIsDeletedFalse(Long taskId, Long customFieldId);

    @Modifying
    @Query("UPDATE CustomFieldValue cv SET cv.isDeleted = true, cv.updatedAt = CURRENT_TIMESTAMP, cv.updatedBy = :actorId WHERE cv.task.id = :taskId AND cv.isDeleted = false")
    void deleteByTaskId(@Param("taskId") Long taskId, @Param("actorId") String actorId);

    @Modifying
    @Query("UPDATE CustomFieldValue cv SET cv.isDeleted = true, cv.updatedAt = CURRENT_TIMESTAMP, cv.updatedBy = :actorId WHERE cv.customField.id = :customFieldId AND cv.isDeleted = false")
    void deleteByCustomFieldId(@Param("customFieldId") Long customFieldId, @Param("actorId") String actorId);
}