package org.SprintForge.platform.monitoring.dto;

import java.time.Instant;
import java.util.Map;

public class HealthCheckResponse {
    private String status; // UP, DOWN, DEGRADED
    private Map<String, Object> components;
    private Instant checkedAt;

    public HealthCheckResponse() {}

    public HealthCheckResponse(String status, Map<String, Object> components, Instant checkedAt) {
        this.status = status;
        this.components = components;
        this.checkedAt = checkedAt;
    }

    public static HealthCheckResponseBuilder builder() {
        return new HealthCheckResponseBuilder();
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Map<String, Object> getComponents() { return components; }
    public void setComponents(Map<String, Object> components) { this.components = components; }

    public Instant getCheckedAt() { return checkedAt; }
    public void setCheckedAt(Instant checkedAt) { this.checkedAt = checkedAt; }

    public static class HealthCheckResponseBuilder {
        private String status;
        private Map<String, Object> components;
        private Instant checkedAt;

        public HealthCheckResponseBuilder status(String status) { this.status = status; return this; }
        public HealthCheckResponseBuilder components(Map<String, Object> components) { this.components = components; return this; }
        public HealthCheckResponseBuilder checkedAt(Instant checkedAt) { this.checkedAt = checkedAt; return this; }

        public HealthCheckResponse build() {
            return new HealthCheckResponse(status, components, checkedAt);
        }
    }
}
