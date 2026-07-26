package org.SprintForge.modules.workspace.kanban.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "board_cards")
@Getter
@Setter
public class BoardCard extends SoftDeleteEntity {
}
