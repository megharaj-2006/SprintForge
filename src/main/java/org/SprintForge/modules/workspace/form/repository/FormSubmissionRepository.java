package org.SprintForge.modules.workspace.form.repository;

import org.SprintForge.modules.workspace.form.entity.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long>, JpaSpecificationExecutor<FormSubmission> {
}