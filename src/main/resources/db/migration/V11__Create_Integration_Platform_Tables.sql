-- V11__Create_Integration_Platform_Tables.sql
-- Database migration for SprintForge Integration Platform - Cluster 1

CREATE TABLE IF NOT EXISTS integration_providers (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    provider_key VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    description TEXT,
    provider_type VARCHAR(50) NOT NULL DEFAULT 'REST',
    category VARCHAR(100) NOT NULL DEFAULT 'GENERAL',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    provider_version VARCHAR(50) NOT NULL DEFAULT '1.0.0',
    icon VARCHAR(100),
    documentation_url VARCHAR(500),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS credential_references (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    credential_key VARCHAR(100) NOT NULL UNIQUE,
    credential_type VARCHAR(50) NOT NULL DEFAULT 'API_KEY',
    encrypted_reference TEXT NOT NULL,
    scope VARCHAR(50) NOT NULL DEFAULT 'WORKSPACE',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS integration_connections (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT,
    provider_id BIGINT NOT NULL REFERENCES integration_providers(id) ON DELETE CASCADE,
    connection_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    authentication_type VARCHAR(50) NOT NULL DEFAULT 'API_KEY',
    credential_reference_id BIGINT REFERENCES credential_references(id) ON DELETE SET NULL,
    base_url VARCHAR(500),
    configuration TEXT,
    health_status VARCHAR(50) DEFAULT 'HEALTHY',
    last_connected TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS connector_capabilities (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    capability_key VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100) NOT NULL DEFAULT 'GENERAL',
    supported_operations TEXT,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS connector_executions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    connection_id BIGINT REFERENCES integration_connections(id) ON DELETE CASCADE,
    capability_key VARCHAR(100) NOT NULL,
    workflow_execution_id BIGINT,
    status VARCHAR(50) NOT NULL DEFAULT 'COMPLETED',
    started_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    duration_ms BIGINT,
    request_metadata TEXT,
    response_metadata TEXT,
    error_message TEXT
);

CREATE TABLE IF NOT EXISTS integration_configurations (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT UNIQUE,
    default_timeout_seconds INT DEFAULT 30,
    retry_policy VARCHAR(100) DEFAULT 'EXPONENTIAL_BACKOFF',
    rate_limit_per_minute INT DEFAULT 120,
    logging_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    monitoring_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Seed initial Integration Providers
INSERT INTO integration_providers (provider_key, display_name, description, provider_type, category, enabled, provider_version)
VALUES 
  ('rest.generic', 'Generic REST Connector', 'Generic HTTP REST integration provider', 'REST', 'CORE', true, '1.0.0'),
  ('webhook.generic', 'Generic Webhook Receiver & Dispatcher', 'Generic Webhook HTTP integration provider', 'WEBHOOK', 'CORE', true, '1.0.0'),
  ('graphql.generic', 'Generic GraphQL Connector', 'Generic GraphQL API provider', 'GRAPHQL', 'CORE', true, '1.0.0')
ON CONFLICT (provider_key) DO NOTHING;

-- Seed initial Connector Capabilities
INSERT INTO connector_capabilities (capability_key, display_name, category, supported_operations)
VALUES 
  ('CREATE_ISSUE', 'Create Issue', 'PROJECT_MANAGEMENT', 'POST'),
  ('SEND_MESSAGE', 'Send Message', 'COMMUNICATION', 'POST'),
  ('UPLOAD_FILE', 'Upload File', 'FILE_STORAGE', 'POST,PUT'),
  ('CREATE_EVENT', 'Create Calendar Event', 'CALENDAR', 'POST'),
  ('SEARCH', 'Search Records', 'SEARCH', 'GET,POST'),
  ('DOWNLOAD', 'Download File', 'FILE_STORAGE', 'GET'),
  ('UPDATE', 'Update Record', 'GENERAL', 'PUT,PATCH'),
  ('DELETE', 'Delete Record', 'GENERAL', 'DELETE')
ON CONFLICT (capability_key) DO NOTHING;
