-- V8__Create_Workflow_Platform_Tables.sql
-- Database migration for SprintForge Workflow Platform - Cluster 3

CREATE TABLE IF NOT EXISTS workflow_secrets (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT,
    name VARCHAR(255) NOT NULL,
    value_encrypted TEXT NOT NULL,
    secret_type VARCHAR(50) NOT NULL DEFAULT 'STRING',
    scope VARCHAR(50) NOT NULL DEFAULT 'WORKSPACE',
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT uk_workspace_secret_name UNIQUE (workspace_id, name)
);

CREATE TABLE IF NOT EXISTS workflow_environments (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS environment_variables (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    environment_id BIGINT NOT NULL REFERENCES workflow_environments(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    value_encrypted TEXT NOT NULL,
    masked BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS workflow_policies (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT,
    name VARCHAR(255) NOT NULL,
    require_approval_before_publish BOOLEAN NOT NULL DEFAULT FALSE,
    max_execution_duration_seconds BIGINT DEFAULT 3600,
    max_parallelism INT DEFAULT 10,
    max_memory_mb INT DEFAULT 512,
    rate_limit_per_minute INT DEFAULT 60,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS workflow_ownerships (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_id BIGINT NOT NULL UNIQUE REFERENCES workflows(id) ON DELETE CASCADE,
    owner_user_id VARCHAR(255) NOT NULL,
    owner_team_id VARCHAR(255),
    transferred_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS workflow_permissions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_id BIGINT NOT NULL REFERENCES workflows(id) ON DELETE CASCADE,
    principal_id VARCHAR(255) NOT NULL,
    principal_type VARCHAR(50) NOT NULL DEFAULT 'USER',
    permission_level VARCHAR(50) NOT NULL DEFAULT 'EXECUTE'
);

CREATE TABLE IF NOT EXISTS marketplace_templates (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    template_key VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100) NOT NULL,
    author VARCHAR(255),
    version_label VARCHAR(50) NOT NULL DEFAULT '1.0.0',
    wdl_payload TEXT NOT NULL,
    rating DOUBLE PRECISION DEFAULT 5.0,
    downloads_count BIGINT DEFAULT 0,
    public_visible BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS plugin_manifests (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    package_id VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    plugin_version VARCHAR(50) NOT NULL,
    manifest_json TEXT NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    installed_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
