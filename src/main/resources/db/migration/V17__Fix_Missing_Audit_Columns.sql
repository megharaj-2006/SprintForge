-- ============================================================================
-- SprintForge Migration V17: Fix Missing Audit Columns
-- Adds created_by / updated_by to tables that were created before entities
-- were refactored to extend AuditableEntity (these columns were absent from
-- the original Hibernate ddl-auto=update run).
-- ============================================================================

ALTER TABLE ai_providers
    ADD COLUMN IF NOT EXISTS created_by  VARCHAR(255),
    ADD COLUMN IF NOT EXISTS updated_by  VARCHAR(255);

ALTER TABLE integration_connections
    ADD COLUMN IF NOT EXISTS updated_by  VARCHAR(255);

ALTER TABLE integration_providers
    ADD COLUMN IF NOT EXISTS created_by  VARCHAR(255),
    ADD COLUMN IF NOT EXISTS updated_by  VARCHAR(255);

ALTER TABLE mgmt_feature_flags
    ADD COLUMN IF NOT EXISTS created_by  VARCHAR(255),
    ADD COLUMN IF NOT EXISTS updated_by  VARCHAR(255);

ALTER TABLE node_definitions
    ADD COLUMN IF NOT EXISTS created_by  VARCHAR(255),
    ADD COLUMN IF NOT EXISTS updated_by  VARCHAR(255);

ALTER TABLE workflow_secrets
    ADD COLUMN IF NOT EXISTS updated_by  VARCHAR(255);
