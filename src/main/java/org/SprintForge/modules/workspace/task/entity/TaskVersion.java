package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_versions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskVersion extends SoftDeleteEntity {

    @Column(name = "task_id", nullable = false)
    private Long taskId;

    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;

    @Column(name = "snapshot_json", nullable = false, columnDefinition = "TEXT")
    private String snapshotJson;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;
}
