-- V12__Create_Connector_Packages_Tables.sql
-- Database migration for SprintForge Enterprise Connector Library - Cluster 2

CREATE TABLE IF NOT EXISTS connector_packages (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    package_key VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100) NOT NULL DEFAULT 'ENTERPRISE',
    package_version VARCHAR(50) NOT NULL DEFAULT '1.0.0',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    installed_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS connector_operations (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    package_key VARCHAR(100) NOT NULL,
    operation_key VARCHAR(100) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    description TEXT,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_package_operation UNIQUE (package_key, operation_key)
);

-- Seed initial Connector Packages
INSERT INTO connector_packages (package_key, name, description, category, package_version)
VALUES 
  ('github', 'GitHub Connector Package', 'GitHub REST API v3 integration package', 'DEVELOPMENT', '1.0.0'),
  ('slack', 'Slack Connector Package', 'Slack Web API integration package', 'COMMUNICATION', '1.0.0'),
  ('microsoft', 'Microsoft Graph Connector Package', 'Microsoft Teams, Outlook, Calendar & Graph API package', 'ENTERPRISE', '1.0.0'),
  ('google', 'Google Workspace Connector Package', 'Gmail, Google Calendar, Drive & Sheets package', 'ENTERPRISE', '1.0.0'),
  ('jira', 'Atlassian Jira Connector Package', 'Jira Cloud REST API package', 'PROJECT_MANAGEMENT', '1.0.0'),
  ('linear', 'Linear GraphQL Connector Package', 'Linear issue tracking package', 'PROJECT_MANAGEMENT', '1.0.0'),
  ('notion', 'Notion Connector Package', 'Notion REST API workspace package', 'PRODUCTIVITY', '1.0.0'),
  ('rest', 'Generic REST Connector Package', 'Generic HTTP REST integration package', 'CORE', '1.0.0'),
  ('webhook', 'Generic Webhook Connector Package', 'Webhook receiver and dispatcher package', 'CORE', '1.0.0')
ON CONFLICT (package_key) DO NOTHING;
