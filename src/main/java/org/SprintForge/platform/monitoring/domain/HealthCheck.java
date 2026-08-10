package org.SprintForge.platform.monitoring.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity(name = "PlatformHealthCheck")
@Table(name = "platform_health_checks")
public class HealthCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "component", nullable = false)
    private String component;

    @Column(name = "status", nullable = false)
    private String status; // UP, DOWN, DEGRADED, WARNING

    @Column(name = "response_time_ms", nullable = false)
    private long responseTimeMs;

    @Column(name = "checked_at", nullable = false)
    private Instant checkedAt = Instant.now();

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    public HealthCheck() {}

    public HealthCheck(Long id, String component, String status, long responseTimeMs, Instant checkedAt, String details) {
        this.id = id;
        this.component = component;
        this.status = status;
        this.responseTimeMs = responseTimeMs;
        this.checkedAt = checkedAt != null ? checkedAt : Instant.now();
        this.details = details;
    }

    public static HealthCheckBuilder builder() {
        return new HealthCheckBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(long responseTimeMs) { this.responseTimeMs = responseTimeMs; }

    public Instant getCheckedAt() { return checkedAt; }
    public void setCheckedAt(Instant checkedAt) { this.checkedAt = checkedAt; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public static class HealthCheckBuilder {
        private Long id;
        private String component;
        private String status;
        private long responseTimeMs;
        private Instant checkedAt = Instant.now();
        private String details;

        public HealthCheckBuilder id(Long id) { this.id = id; return this; }
        public HealthCheckBuilder component(String component) { this.component = component; return this; }
        public HealthCheckBuilder status(String status) { this.status = status; return this; }
        public HealthCheckBuilder responseTimeMs(long responseTimeMs) { this.responseTimeMs = responseTimeMs; return this; }
        public HealthCheckBuilder checkedAt(Instant checkedAt) { this.checkedAt = checkedAt; return this; }
        public HealthCheckBuilder details(String details) { this.details = details; return this; }

        public HealthCheck build() {
            return new HealthCheck(id, component, status, responseTimeMs, checkedAt, details);
        }
    }
}
