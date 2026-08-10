package org.SprintForge.modules.workflow.marketplace;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.SprintForge.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "marketplace_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceTemplate extends BaseEntity {

    @Column(name = "template_key", nullable = false, unique = true)
    private String templateKey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "author")
    private String author;

    @Column(name = "version_label", nullable = false)
    private String versionLabel = "1.0.0";

    @Column(name = "wdl_payload", nullable = false, columnDefinition = "TEXT")
    private String wdlPayload;

    @Column(name = "rating")
    private Double rating = 5.0;

    @Column(name = "downloads_count")
    private Long downloadsCount = 0L;

    @Column(name = "public_visible", nullable = false)
    private Boolean publicVisible = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
