package org.SprintForge.modules.workspace.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment extends SoftDeleteEntity {
}
