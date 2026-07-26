package org.SprintForge.modules.workspace.customfield.repository;

import org.SprintForge.modules.workspace.customfield.entity.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomFieldRepository extends JpaRepository<CustomField, Long>, JpaSpecificationExecutor<CustomField> {
}