-- V7__Create_Workflow_Engine_Tables.sql
-- Database migration for SprintForge Workflow Engine - Cluster 1

CREATE TABLE IF NOT EXISTS workflows (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    current_version_id BIGINT,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS workflow_versions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_id BIGINT NOT NULL REFERENCES workflows(id) ON DELETE CASCADE,
    version_number INT NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    published_at TIMESTAMP WITHOUT TIME ZONE,
    change_summary TEXT,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_workflow_version_number UNIQUE (workflow_id, version_number)
);

-- Circular FK reference for workflows.current_version_id
ALTER TABLE workflows ADD CONSTRAINT fk_workflows_current_version 
    FOREIGN KEY (current_version_id) REFERENCES workflow_versions(id) ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS node_definitions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    node_key VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    configuration_schema TEXT,
    output_schema TEXT,
    definition_version VARCHAR(50) NOT NULL DEFAULT '1.0',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS workflow_nodes (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_version_id BIGINT NOT NULL REFERENCES workflow_versions(id) ON DELETE CASCADE,
    definition_id BIGINT NOT NULL REFERENCES node_definitions(id),
    name VARCHAR(255) NOT NULL,
    node_type VARCHAR(100) NOT NULL,
    position_x DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    position_y DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS workflow_connections (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_version_id BIGINT NOT NULL REFERENCES workflow_versions(id) ON DELETE CASCADE,
    source_node_id BIGINT NOT NULL REFERENCES workflow_nodes(id) ON DELETE CASCADE,
    target_node_id BIGINT NOT NULL REFERENCES workflow_nodes(id) ON DELETE CASCADE,
    branch_label VARCHAR(50) NOT NULL DEFAULT 'DEFAULT',
    priority INT NOT NULL DEFAULT 0,
    condition_expression TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS workflow_variables (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_version_id BIGINT NOT NULL REFERENCES workflow_versions(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL DEFAULT 'STRING',
    scope VARCHAR(50) NOT NULL DEFAULT 'GLOBAL',
    default_value TEXT,
    required BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uk_workflow_version_variable_name UNIQUE (workflow_version_id, name)
);

CREATE TABLE IF NOT EXISTS workflow_executions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_version_id BIGINT NOT NULL REFERENCES workflow_versions(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    triggered_by VARCHAR(255),
    trigger_source VARCHAR(255),
    started_at TIMESTAMP WITHOUT TIME ZONE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    duration BIGINT,
    current_node_id BIGINT REFERENCES workflow_nodes(id) ON DELETE SET NULL,
    context_id VARCHAR(255),
    result TEXT,
    error TEXT
);

CREATE TABLE IF NOT EXISTS node_executions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_execution_id BIGINT NOT NULL REFERENCES workflow_executions(id) ON DELETE CASCADE,
    workflow_node_id BIGINT NOT NULL REFERENCES workflow_nodes(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    started_at TIMESTAMP WITHOUT TIME ZONE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    duration BIGINT,
    input TEXT,
    output TEXT,
    error TEXT
);

CREATE TABLE IF NOT EXISTS execution_contexts (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workflow_execution_id BIGINT NOT NULL UNIQUE REFERENCES workflow_executions(id) ON DELETE CASCADE,
    variables TEXT,
    runtime_state TEXT,
    metadata TEXT,
    correlation_id VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Seed basic Node Definitions
INSERT INTO node_definitions (node_key, display_name, category, configuration_schema, output_schema, definition_version, enabled, created_at)
VALUES 
    ('manual.trigger', 'Manual Trigger', 'TRIGGER', '{"type":"object"}', '{"type":"object"}', '1.0', TRUE, CURRENT_TIMESTAMP),
    ('delay.logic', 'Delay Logic', 'LOGIC', '{"type":"object","properties":{"durationMillis":{"type":"integer"}}}', '{"type":"object"}', '1.0', TRUE, CURRENT_TIMESTAMP),
    ('approval.human', 'Human Approval', 'HUMAN', '{"type":"object","properties":{"approverRole":{"type":"string"}}}', '{"type":"object"}', '1.0', TRUE, CURRENT_TIMESTAMP),
    ('passthrough.logic', 'Pass-Through', 'LOGIC', '{"type":"object"}', '{"type":"object"}', '1.0', TRUE, CURRENT_TIMESTAMP)
ON CONFLICT (node_key) DO NOTHING;
