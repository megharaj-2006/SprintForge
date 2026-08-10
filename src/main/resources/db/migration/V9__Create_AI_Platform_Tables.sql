-- V9__Create_AI_Platform_Tables.sql
-- Database migration for SprintForge AI Platform - Cluster 1

CREATE TABLE IF NOT EXISTS ai_providers (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    provider_key VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    provider_type VARCHAR(50) NOT NULL DEFAULT 'OPENAI',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    priority INT NOT NULL DEFAULT 1,
    base_url VARCHAR(500),
    authentication_type VARCHAR(50) NOT NULL DEFAULT 'BEARER_TOKEN',
    default_model VARCHAR(100),
    configuration TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE IF NOT EXISTS ai_models (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    provider_id BIGINT NOT NULL REFERENCES ai_providers(id) ON DELETE CASCADE,
    model_key VARCHAR(100) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    capabilities VARCHAR(500),
    context_window INT NOT NULL DEFAULT 8192,
    max_output_tokens INT NOT NULL DEFAULT 4096,
    supports_streaming BOOLEAN NOT NULL DEFAULT TRUE,
    supports_vision BOOLEAN NOT NULL DEFAULT FALSE,
    supports_function_calling BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_provider_model_key UNIQUE (provider_id, model_key)
);

CREATE TABLE IF NOT EXISTS prompt_templates (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    template_key VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100) NOT NULL DEFAULT 'GENERAL',
    system_prompt TEXT,
    user_prompt TEXT NOT NULL,
    output_schema TEXT,
    template_version VARCHAR(50) NOT NULL DEFAULT '1.0.0',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS prompt_variables (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    template_id BIGINT NOT NULL REFERENCES prompt_templates(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    variable_type VARCHAR(50) NOT NULL DEFAULT 'STRING',
    required BOOLEAN NOT NULL DEFAULT TRUE,
    default_value TEXT,
    description TEXT
);

CREATE TABLE IF NOT EXISTS ai_capabilities (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    capability_key VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(50) NOT NULL DEFAULT 'GENERATION',
    default_provider_key VARCHAR(100),
    default_model_key VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS ai_executions (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    capability_key VARCHAR(100) NOT NULL,
    provider_key VARCHAR(100) NOT NULL,
    model_key VARCHAR(100) NOT NULL,
    workflow_execution_id BIGINT,
    prompt_template_id BIGINT REFERENCES prompt_templates(id) ON DELETE SET NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'COMPLETED',
    started_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    duration_ms BIGINT,
    input_tokens INT DEFAULT 0,
    output_tokens INT DEFAULT 0,
    estimated_cost DOUBLE PRECISION DEFAULT 0.0,
    error_message TEXT
);

CREATE TABLE IF NOT EXISTS ai_conversation_memories (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    memory_scope VARCHAR(50) NOT NULL DEFAULT 'WORKFLOW',
    scope_id VARCHAR(255) NOT NULL,
    conversation_id VARCHAR(255) NOT NULL,
    messages_json TEXT,
    summary TEXT,
    token_count INT DEFAULT 0,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_memory_scope_conv UNIQUE (memory_scope, scope_id, conversation_id)
);

CREATE TABLE IF NOT EXISTS ai_configurations (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    workspace_id BIGINT UNIQUE,
    default_provider_key VARCHAR(100) NOT NULL DEFAULT 'openai',
    default_model_key VARCHAR(100) NOT NULL DEFAULT 'gpt-4o',
    max_tokens INT DEFAULT 2048,
    temperature DOUBLE PRECISION DEFAULT 0.7,
    timeout_seconds INT DEFAULT 30,
    retry_policy VARCHAR(100) DEFAULT 'EXPONENTIAL_BACKOFF',
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Seed initial AI Providers
INSERT INTO ai_providers (provider_key, display_name, provider_type, enabled, priority, default_model)
VALUES 
  ('openai', 'OpenAI AI Platform', 'OPENAI', true, 1, 'gpt-4o'),
  ('gemini', 'Google Gemini AI', 'GEMINI', true, 2, 'gemini-1.5-pro'),
  ('anthropic', 'Anthropic Claude AI', 'ANTHROPIC', true, 3, 'claude-3-5-sonnet'),
  ('ollama', 'Ollama Local LLM', 'OLLAMA', true, 4, 'llama3')
ON CONFLICT (provider_key) DO NOTHING;

-- Seed initial AI Capabilities
INSERT INTO ai_capabilities (capability_key, display_name, category, default_provider_key, default_model_key)
VALUES 
  ('ai.generate', 'Text Generation', 'GENERATION', 'openai', 'gpt-4o'),
  ('ai.summarize', 'Text Summarization', 'SUMMARIZATION', 'gemini', 'gemini-1.5-pro'),
  ('ai.classify', 'Text Classification', 'CLASSIFICATION', 'openai', 'gpt-4o-mini'),
  ('ai.extract', 'Entity Extraction', 'EXTRACTION', 'openai', 'gpt-4o'),
  ('ai.translate', 'Language Translation', 'TRANSLATION', 'openai', 'gpt-4o-mini'),
  ('ai.reasoning', 'Complex Reasoning', 'REASONING', 'anthropic', 'claude-3-5-sonnet'),
  ('ai.prediction', 'Predictive Analysis', 'PREDICTION', 'openai', 'gpt-4o'),
  ('ai.embedding', 'Vector Embedding', 'EMBEDDING', 'openai', 'text-embedding-3-small')
ON CONFLICT (capability_key) DO NOTHING;
