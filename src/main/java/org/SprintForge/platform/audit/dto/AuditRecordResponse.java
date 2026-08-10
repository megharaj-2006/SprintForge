package org.SprintForge.platform.audit.dto;

import java.time.Instant;

public class AuditRecordResponse {
    private Long id;
    private String workspaceId;
    private String projectId;
    private String entityType;
    private String entityId;
    private String action;
    private String actorId;
    private String oldValue;
    private String newValue;
    private String ipAddress;
    private String userAgent;
    private String correlationId;
    private Instant createdAt;

    public AuditRecordResponse() {}

    public AuditRecordResponse(Long id, String workspaceId, String projectId, String entityType, String entityId, String action, String actorId, String oldValue, String newValue, String ipAddress, String userAgent, String correlationId, Instant createdAt) {
        this.id = id;
        this.workspaceId = workspaceId;
        this.projectId = projectId;
        this.entityType = entityType;
        this.entityId = entityId;
        this.action = action;
        this.actorId = actorId;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
    }

    public static AuditRecordResponseBuilder builder() {
        return new AuditRecordResponseBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWorkspaceId() { return workspaceId; }
    public void setWorkspaceId(String workspaceId) { this.workspaceId = workspaceId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getEntityId() { return entityId; }
    public void setEntityId(String entityId) { this.entityId = entityId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getActorId() { return actorId; }
    public void setActorId(String actorId) { this.actorId = actorId; }

    public String getOldValue() { return oldValue; }
    public void setOldValue(String oldValue) { this.oldValue = oldValue; }

    public String getNewValue() { return newValue; }
    public void setNewValue(String newValue) { this.newValue = newValue; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public static class AuditRecordResponseBuilder {
        private Long id;
        private String workspaceId;
        private String projectId;
        private String entityType;
        private String entityId;
        private String action;
        private String actorId;
        private String oldValue;
        private String newValue;
        private String ipAddress;
        private String userAgent;
        private String correlationId;
        private Instant createdAt;

        public AuditRecordResponseBuilder id(Long id) { this.id = id; return this; }
        public AuditRecordResponseBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public AuditRecordResponseBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public AuditRecordResponseBuilder entityType(String entityType) { this.entityType = entityType; return this; }
        public AuditRecordResponseBuilder entityId(String entityId) { this.entityId = entityId; return this; }
        public AuditRecordResponseBuilder action(String action) { this.action = action; return this; }
        public AuditRecordResponseBuilder actorId(String actorId) { this.actorId = actorId; return this; }
        public AuditRecordResponseBuilder oldValue(String oldValue) { this.oldValue = oldValue; return this; }
        public AuditRecordResponseBuilder newValue(String newValue) { this.newValue = newValue; return this; }
        public AuditRecordResponseBuilder ipAddress(String ipAddress) { this.ipAddress = ipAddress; return this; }
        public AuditRecordResponseBuilder userAgent(String userAgent) { this.userAgent = userAgent; return this; }
        public AuditRecordResponseBuilder correlationId(String correlationId) { this.correlationId = correlationId; return this; }
        public AuditRecordResponseBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public AuditRecordResponse build() {
            return new AuditRecordResponse(id, workspaceId, projectId, entityType, entityId, action, actorId, oldValue, newValue, ipAddress, userAgent, correlationId, createdAt);
        }
    }
}
