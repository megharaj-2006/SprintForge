package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_ai_summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskAISummary extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "generated_by")
    private Long generatedBy;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}

