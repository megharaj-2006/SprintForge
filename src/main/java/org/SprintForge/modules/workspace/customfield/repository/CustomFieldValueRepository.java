package org.SprintForge.modules.workspace.customfield.repository;

import org.SprintForge.modules.workspace.customfield.entity.CustomFieldValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFieldValueRepository extends JpaRepository<CustomFieldValue, Long>, JpaSpecificationExecutor<CustomFieldValue> {
}