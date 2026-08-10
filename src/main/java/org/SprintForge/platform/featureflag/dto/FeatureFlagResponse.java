package org.SprintForge.platform.featureflag.dto;

import java.time.Instant;

public class FeatureFlagResponse {
    private Long id;
    private String name;
    private String description;
    private boolean enabled;
    private String scope;
    private String workspaceId;
    private int rolloutPercentage;
    private Instant createdAt;
    private Instant updatedAt;

    public FeatureFlagResponse() {}

    public FeatureFlagResponse(Long id, String name, String description, boolean enabled, String scope, String workspaceId, int rolloutPercentage, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.scope = scope;
        this.workspaceId = workspaceId;
        this.rolloutPercentage = rolloutPercentage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FeatureFlagResponseBuilder builder() {
        return new FeatureFlagResponseBuilder();
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

    public static class FeatureFlagResponseBuilder {
        private Long id;
        private String name;
        private String description;
        private boolean enabled;
        private String scope;
        private String workspaceId;
        private int rolloutPercentage;
        private Instant createdAt;
        private Instant updatedAt;

        public FeatureFlagResponseBuilder id(Long id) { this.id = id; return this; }
        public FeatureFlagResponseBuilder name(String name) { this.name = name; return this; }
        public FeatureFlagResponseBuilder description(String description) { this.description = description; return this; }
        public FeatureFlagResponseBuilder enabled(boolean enabled) { this.enabled = enabled; return this; }
        public FeatureFlagResponseBuilder scope(String scope) { this.scope = scope; return this; }
        public FeatureFlagResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public FeatureFlagResponseBuilder rolloutPercentage(int rolloutPercentage) { this.rolloutPercentage = rolloutPercentage; return this; }
        public FeatureFlagResponseBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public FeatureFlagResponseBuilder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public FeatureFlagResponse build() {
            return new FeatureFlagResponse(id, name, description, enabled, scope, workspaceId, rolloutPercentage, createdAt, updatedAt);
        }
    }
}
