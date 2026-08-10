package org.SprintForge.platform.audit.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformAuditRecord")
@Table(name = "platform_audit_records")
public class AuditRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id")
    private String workspaceId;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "actor_id", nullable = false)
    private String actorId;

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public AuditRecord() {}

    public AuditRecord(Long id, String workspaceId, String projectId, String entityType, String entityId, String action, String actorId, String oldValue, String newValue, String ipAddress, String userAgent, String correlationId, Instant createdAt) {
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
        this.createdAt = createdAt != null ? createdAt : Instant.now();
    }

    public static AuditRecordBuilder builder() {
        return new AuditRecordBuilder();
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

    public static class AuditRecordBuilder {
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
        private Instant createdAt = Instant.now();

        public AuditRecordBuilder id(Long id) { this.id = id; return this; }
        public AuditRecordBuilder workspaceId(String workspaceId) { this.workspaceId = workspaceId; return this; }
        public AuditRecordBuilder projectId(String projectId) { this.projectId = projectId; return this; }
        public AuditRecordBuilder entityType(String entityType) { this.entityType = entityType; return this; }
        public AuditRecordBuilder entityId(String entityId) { this.entityId = entityId; return this; }
        public AuditRecordBuilder action(String action) { this.action = action; return this; }
        public AuditRecordBuilder actorId(String actorId) { this.actorId = actorId; return this; }
        public AuditRecordBuilder oldValue(String oldValue) { this.oldValue = oldValue; return this; }
        public AuditRecordBuilder newValue(String newValue) { this.newValue = newValue; return this; }
        public AuditRecordBuilder ipAddress(String ipAddress) { this.ipAddress = ipAddress; return this; }
        public AuditRecordBuilder userAgent(String userAgent) { this.userAgent = userAgent; return this; }
        public AuditRecordBuilder correlationId(String correlationId) { this.correlationId = correlationId; return this; }
        public AuditRecordBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }

        public AuditRecord build() {
            return new AuditRecord(id, workspaceId, projectId, entityType, entityId, action, actorId, oldValue, newValue, ipAddress, userAgent, correlationId, createdAt);
        }
    }
}
