package org.SprintForge.modules.workspace.form.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "form_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmission extends SoftDeleteEntity {

    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "submitted_by")
    private Long submittedBy;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "status")
    private String status;

    @Column(name = "created_task_id")
    private Long createdTaskId;

    @Column(name = "submission_data", columnDefinition = "TEXT")
    private String submissionData;
}

