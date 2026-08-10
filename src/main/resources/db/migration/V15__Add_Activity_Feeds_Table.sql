-- ============================================================================
-- SprintForge PostgreSQL Migration V15: Add missing activity_feeds table
-- Ensures existing PostgreSQL databases contain the activity_feeds schema.
-- ============================================================================

CREATE TABLE IF NOT EXISTS activity_feeds (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    project_id BIGINT,
    task_id BIGINT,
    user_id BIGINT NOT NULL,
    activity_type VARCHAR(255) NOT NULL,
    summary TEXT NOT NULL,
    metadata TEXT,
    visibility VARCHAR(255) DEFAULT 'PUBLIC',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_activity_feeds_user ON activity_feeds(user_id);
CREATE INDEX IF NOT EXISTS idx_activity_feeds_workspace ON activity_feeds(workspace_id);
CREATE INDEX IF NOT EXISTS idx_activity_feeds_project ON activity_feeds(project_id);
