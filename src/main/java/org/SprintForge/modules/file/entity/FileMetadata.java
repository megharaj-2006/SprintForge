package org.SprintForge.modules.file.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.SprintForge.common.entity.SoftDeleteEntity;

@Entity
@Table(name = "file_metadatas")
@Getter
@Setter
public class FileMetadata extends SoftDeleteEntity {
}
