package org.SprintForge.modules.workspace.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "project_labels")
@Getter
@Setter
public class ProjectLabel extends SoftDeleteEntity {
}
