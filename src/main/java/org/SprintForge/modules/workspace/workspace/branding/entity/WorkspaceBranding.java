package org.SprintForge.modules.workspace.workspace.branding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.SprintForge.common.entity.AuditableEntity;

@Entity
@Table(name = "workspace_brandings", uniqueConstraints = {
    @UniqueConstraint(columnNames = "workspace_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceBranding extends AuditableEntity {
    @Column(name = "workspace_id", nullable = false, unique = true) private Long workspaceId;
    @Column(name = "logo") private String logo;
    @Column(name = "banner") private String banner;
    @Column(name = "icon") private String icon;
    @Column(name = "theme") private String theme; // LIGHT, DARK, AUTO
    @Column(name = "accent_color") private String accentColor;
    @Column(name = "primary_color") private String primaryColor;
    @Column(name = "font_family") private String fontFamily;
}
