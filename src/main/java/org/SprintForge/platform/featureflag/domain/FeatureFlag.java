package org.SprintForge.platform.featureflag.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformFeatureFlag")
@Table(name = "platform_feature_flags")
public class FeatureFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "scope", nullable = false)
    private String scope = "GLOBAL"; // GLOBAL, WORKSPACE, PROJECT, USER

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "rollout_percentage", nullable = false)
    private int rolloutPercentage = 100;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    public FeatureFlag() {}

    public FeatureFlag(Long id, String name, String description, boolean enabled, String scope, String workspaceId, int rolloutPercentage, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.scope = scope != null ? scope : "GLOBAL";
        this.workspaceId = workspaceId;
        this.rolloutPercentage = rolloutPercentage;
        this.createdAt = createdAt != null ? createdAt : Instant.now();
        this.updatedAt = updatedAt != null ? updatedAt : Instant.now();
    }

    public static FeatureFlagBuilder builder() {
        return new FeatureFlagBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public int getRolloutPercentage() { return rolloutPercentage; }
    public void setRolloutPercentage(int rolloutPercentage) { this.rolloutPercentage = rolloutPercentage; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public static class FeatureFlagBuilder {
        private Long id;
        private String name;
        private String description;
        private boolean enabled = false;
        private String scope = "GLOBAL";
        private String workspaceId;
        private int rolloutPercentage = 100;
        private Instant createdAt = Instant.now();
        private Instant updatedAt = Instant.now();

        public FeatureFlagBuilder id(Long id) { this.id = id; return this; }
        public FeatureFlagBuilder name(String name) { this.name = name; return this; }
        public FeatureFlagBuilder description(String description) { this.description = description; return this; }
        public FeatureFlagBuilder enabled(boolean enabled) { this.enabled = enabled; return this; }
        public FeatureFlagBuilder scope(String scope) { this.scope = scope; return this; }
        public FeatureFlagBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public FeatureFlagBuilder rolloutPercentage(int rolloutPercentage) { this.rolloutPercentage = rolloutPercentage; return this; }
        public FeatureFlagBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public FeatureFlagBuilder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public FeatureFlag build() {
            return new FeatureFlag(id, name, description, enabled, scope, workspaceId, rolloutPercentage, createdAt, updatedAt);
        }
    }
}
