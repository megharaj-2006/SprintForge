package org.SprintForge.modules.workspace.issue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "stories")
@Getter
@Setter
public class Story extends SoftDeleteEntity {
}
