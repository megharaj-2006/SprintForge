package org.SprintForge.modules.workspace.customfield.repository;

import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.SprintForge.modules.workspace.customfield.entity.enums.CustomFieldType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomFieldRepository extends JpaRepository<CustomField, Long>, JpaSpecificationExecutor<CustomField> {

    List<CustomField> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<CustomField> findByFieldTypeAndIsDeletedFalse(CustomFieldType fieldType);

    boolean existsByProjectIdAndNameAndIsDeletedFalse(Long projectId, String name);

    @Query("SELECT c FROM CustomField c WHERE c.project.id = :projectId AND c.isDeleted = false AND " +
           "(LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<CustomField> searchFields(@Param("projectId") Long projectId, @Param("query") String query);
}