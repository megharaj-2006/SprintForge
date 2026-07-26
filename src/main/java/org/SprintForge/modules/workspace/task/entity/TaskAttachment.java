package org.SprintForge.modules.workspace.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "task_attachments")
@Getter
@Setter
public class TaskAttachment extends SoftDeleteEntity {
}
