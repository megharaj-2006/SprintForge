-- V13__Create_Platform_Management_Tables.sql
-- Database migration for SprintForge Platform Management - Cluster 1

CREATE TABLE IF NOT EXISTS mgmt_feature_flags (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    flag_key VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    description TEXT,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    rollout_percentage INT DEFAULT 100,
    scope VARCHAR(50) NOT NULL DEFAULT 'GLOBAL',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS mgmt_installed_packages (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    package_key VARCHAR(100) NOT NULL UNIQUE,
    package_name VARCHAR(255) NOT NULL,
    version_string VARCHAR(50) NOT NULL DEFAULT '1.0.0',
    status VARCHAR(50) NOT NULL DEFAULT 'ENABLED',
    installed_by VARCHAR(255),
    installed_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mgmt_security_policies (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    policy_key VARCHAR(100) NOT NULL UNIQUE,
    policy_type VARCHAR(50) NOT NULL DEFAULT 'PASSWORD',
    configuration TEXT,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mgmt_licenses (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    license_key VARCHAR(100) NOT NULL UNIQUE,
    license_tier VARCHAR(50) NOT NULL DEFAULT 'ENTERPRISE',
    organization_name VARCHAR(255) NOT NULL,
    max_workspaces INT DEFAULT 100,
    max_seats INT DEFAULT 1000,
    valid_until TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mgmt_audit_logs (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    action VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL DEFAULT 'PLATFORM',
    severity VARCHAR(50) NOT NULL DEFAULT 'INFO',
    actor VARCHAR(255),
    details TEXT,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Seed Initial Feature Flags
INSERT INTO mgmt_feature_flags (flag_key, display_name, description, enabled, rollout_percentage, scope)
VALUES
  ('workflow_simulation_enabled', 'Enable Workflow Simulation', 'Allow running dry-run simulations in workflow engine', true, 100, 'GLOBAL'),
  ('ai_skills_enabled', 'Enable AI Skills Engine', 'Allow orchestrating AI capabilities via skills', true, 100, 'GLOBAL'),
  ('integration_webhooks_enabled', 'Enable Webhook Dispatcher', 'Allow sending and receiving integration webhooks', true, 100, 'GLOBAL')
ON CONFLICT (flag_key) DO NOTHING;

-- Seed Initial Installed Packages
INSERT INTO mgmt_installed_packages (package_key, package_name, version_string, status, installed_by)
VALUES
  ('workflow', 'Workflow Engine Package', '3.0.0', 'ENABLED', 'SYSTEM'),
  ('ai', 'AI Platform Package', '2.0.0', 'ENABLED', 'SYSTEM'),
  ('integration', 'Integration Platform Package', '2.0.0', 'ENABLED', 'SYSTEM')
ON CONFLICT (package_key) DO NOTHING;

-- Seed Default Enterprise License
INSERT INTO mgmt_licenses (license_key, license_tier, organization_name, max_workspaces, max_seats, status)
VALUES
  ('LIC-ENT-2026-001', 'ENTERPRISE', 'SprintForge Enterprise Customer', 500, 5000, 'ACTIVE')
ON CONFLICT (license_key) DO NOTHING;

-- Seed Default Security Policy
INSERT INTO mgmt_security_policies (policy_key, policy_type, configuration, enabled)
VALUES
  ('password_policy_default', 'PASSWORD', '{"minLength":12,"requireSpecial":true,"expiryDays":90}', true),
  ('session_policy_default', 'SESSION', '{"maxIdleMinutes":30,"maxConcurrentSessions":3}', true)
ON CONFLICT (policy_key) DO NOTHING;
