-- Flyway Migration V6: Create Shared Infrastructure Platform Tables

CREATE TABLE IF NOT EXISTS platform_audit_records (
    id BIGSERIAL PRIMARY KEY,
    workspace_id VARCHAR(64),
    project_id VARCHAR(64),
    entity_type VARCHAR(64) NOT NULL,
    entity_id VARCHAR(64) NOT NULL,
    action VARCHAR(64) NOT NULL,
    actor_id VARCHAR(64) NOT NULL,
    old_value TEXT,
    new_value TEXT,
    ip_address VARCHAR(64),
    user_agent VARCHAR(255),
    correlation_id VARCHAR(128),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_platform_audit_entity ON platform_audit_records(entity_type, entity_id);
CREATE INDEX idx_platform_audit_actor ON platform_audit_records(actor_id);
CREATE INDEX idx_platform_audit_workspace ON platform_audit_records(workspace_id);

CREATE TABLE IF NOT EXISTS platform_analytics_snapshots (
    id BIGSERIAL PRIMARY KEY,
    workspace_id VARCHAR(64),
    project_id VARCHAR(64),
    snapshot_type VARCHAR(64) NOT NULL,
    metrics TEXT,
    captured_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS platform_reports (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    report_type VARCHAR(64) NOT NULL,
    filters TEXT,
    generated_by VARCHAR(64) NOT NULL,
    generated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    format VARCHAR(32) NOT NULL,
    download_url VARCHAR(512),
    status VARCHAR(32) NOT NULL DEFAULT 'COMPLETED'
);

CREATE TABLE IF NOT EXISTS platform_cache_entries (
    id BIGSERIAL PRIMARY KEY,
    cache_key VARCHAR(255) UNIQUE NOT NULL,
    cache_group VARCHAR(64) NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_accessed TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS platform_feature_flags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    scope VARCHAR(32) NOT NULL DEFAULT 'GLOBAL',
    workspace_id VARCHAR(64),
    rollout_percentage INT NOT NULL DEFAULT 100,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS platform_health_checks (
    id BIGSERIAL PRIMARY KEY,
    component VARCHAR(64) NOT NULL,
    status VARCHAR(32) NOT NULL,
    response_time_ms BIGINT NOT NULL,
    checked_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    details TEXT
);
