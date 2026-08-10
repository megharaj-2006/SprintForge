-- V10__Create_AI_Services_Tables.sql
-- Database migration for SprintForge AI Platform Cluster 2

CREATE TABLE IF NOT EXISTS ai_execution_policies (
    id BIGSERIAL PRIMARY KEY,
    version BIGINT NOT NULL DEFAULT 0,
    policy_key VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    preferred_provider VARCHAR(100),
    preferred_model VARCHAR(100),
    fallback_provider VARCHAR(100),
    fallback_model VARCHAR(100),
    max_cost DOUBLE PRECISION DEFAULT 0.05,
    max_latency_ms INT DEFAULT 5000,
    privacy_level VARCHAR(50) DEFAULT 'STANDARD',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Seed initial Execution Policies
INSERT INTO ai_execution_policies (policy_key, name, description, preferred_provider, preferred_model, fallback_provider, fallback_model, max_cost, max_latency_ms)
VALUES 
  ('BALANCED', 'Balanced Default Policy', 'Default policy balancing latency, cost, and quality', 'openai', 'gpt-4o', 'gemini', 'gemini-1.5-pro'),
  ('FASTEST', 'Low Latency Policy', 'Optimized for minimum latency responses', 'ollama', 'llama3', 'openai', 'gpt-4o-mini'),
  ('CHEAPEST', 'Cost Optimized Policy', 'Optimized for minimum token execution cost', 'gemini', 'gemini-1.5-pro', 'openai', 'gpt-4o-mini'),
  ('HIGHEST_QUALITY', 'Highest Quality Reasoning', 'Uses high-capacity LLM models for complex tasks', 'anthropic', 'claude-3-5-sonnet', 'openai', 'gpt-4o'),
  ('PRIVATE_ONLY', 'Local/Private Only', 'Executes strictly on local models', 'ollama', 'llama3', NULL, NULL)
ON CONFLICT (policy_key) DO NOTHING;
