-- ============================================================================
-- SprintForge PostgreSQL Database Schema
-- All tables contain standard BaseEntity audit columns and mapping relations.
-- ============================================================================

-- Drop tables if they exist (Clean execution)
DROP TABLE IF EXISTS admin_action_logs CASCADE;
DROP TABLE IF EXISTS system_configurations CASCADE;
DROP TABLE IF EXISTS project_statisticss CASCADE;
DROP TABLE IF EXISTS sprint_metricss CASCADE;
DROP TABLE IF EXISTS dashboard_metricss CASCADE;
DROP TABLE IF EXISTS file_versions CASCADE;
DROP TABLE IF EXISTS file_metadatas CASCADE;
DROP TABLE IF EXISTS audit_logs CASCADE;
DROP TABLE IF EXISTS activity_logs CASCADE;
DROP TABLE IF EXISTS notification_preferences CASCADE;
DROP TABLE IF EXISTS notifications CASCADE;
DROP TABLE IF EXISTS reactions CASCADE;
DROP TABLE IF EXISTS mentions CASCADE;
DROP TABLE IF EXISTS comment_replies CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS issues CASCADE;
DROP TABLE IF EXISTS board_cards CASCADE;
DROP TABLE IF EXISTS board_columns CASCADE;
DROP TABLE IF EXISTS boards CASCADE;
DROP TABLE IF EXISTS task_histories CASCADE;
DROP TABLE IF EXISTS task_tags CASCADE;
DROP TABLE IF EXISTS task_labels CASCADE;
DROP TABLE IF EXISTS task_attachments CASCADE;
DROP TABLE IF EXISTS task_comments CASCADE;
DROP TABLE IF EXISTS task_assignments CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS stories CASCADE;
DROP TABLE IF EXISTS epics CASCADE;
DROP TABLE IF EXISTS sprint_statisticss CASCADE;
DROP TABLE IF EXISTS sprint_goals CASCADE;
DROP TABLE IF EXISTS sprints CASCADE;
DROP TABLE IF EXISTS project_labels CASCADE;
DROP TABLE IF EXISTS project_categories CASCADE;
DROP TABLE IF EXISTS project_members CASCADE;
DROP TABLE IF EXISTS projects CASCADE;
DROP TABLE IF EXISTS workspace_invitations CASCADE;
DROP TABLE IF EXISTS workspace_members CASCADE;
DROP TABLE IF EXISTS workspaces CASCADE;
DROP TABLE IF EXISTS password_reset_tokens CASCADE;
DROP TABLE IF EXISTS email_verification_tokens CASCADE;
DROP TABLE IF EXISTS refresh_tokens CASCADE;
DROP TABLE IF EXISTS permissions CASCADE;
DROP TABLE IF EXISTS roles CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- 1. Roles
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE, -- Admin, Workspace Owner, Manager, Developer, Viewer
    description VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 2. Users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    profile_picture VARCHAR(500),
    provider VARCHAR(50) NOT NULL DEFAULT 'LOCAL',
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    role_id BIGINT REFERENCES roles(id) ON DELETE SET NULL,
    last_login TIMESTAMP WITHOUT TIME ZONE,
    is_suspended BOOLEAN NOT NULL DEFAULT FALSE,
    bio TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);
-- 3. Permissions
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 4. Refresh Tokens
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    user_agent VARCHAR(255),
    ip_address VARCHAR(100),
    last_used TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 5. Email Verification Tokens
CREATE TABLE email_verification_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 6. Password Reset Tokens
CREATE TABLE password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 7. Workspaces
CREATE TABLE workspaces (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    owner_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 8. Workspace Members
CREATE TABLE workspace_members (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_workspace_member UNIQUE (workspace_id, user_id)
);

-- 9. Workspace Invitations
CREATE TABLE workspace_invitations (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    invitee_email VARCHAR(255) NOT NULL,
    inviter_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES roles(id),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING', -- PENDING, ACCEPTED, REJECTED, EXPIRED, CANCELLED
    token VARCHAR(255) UNIQUE,
    message TEXT,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    accepted_at TIMESTAMP WITHOUT TIME ZONE,
    rejected_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);


-- 10. Projects
CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    project_key VARCHAR(10) NOT NULL,
    description TEXT,
    icon VARCHAR(255),
    cover_image VARCHAR(255),
    color VARCHAR(50),
    visibility VARCHAR(50) DEFAULT 'WORKSPACE',
    status VARCHAR(50) DEFAULT 'PLANNING',
    owner_id BIGINT REFERENCES users(id),
    start_date DATE,
    target_end_date DATE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    progress_percentage DOUBLE PRECISION DEFAULT 0.0,
    budget DOUBLE PRECISION,
    currency VARCHAR(10) DEFAULT 'USD',
    estimated_hours DOUBLE PRECISION,
    logged_hours DOUBLE PRECISION DEFAULT 0.0,
    is_template BOOLEAN NOT NULL DEFAULT FALSE,
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 11. Project Roles
CREATE TABLE project_roles (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    color VARCHAR(50),
    permissions TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 12. Project Members
CREATE TABLE project_members (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    workspace_member_id BIGINT NOT NULL REFERENCES workspace_members(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES project_roles(id) ON DELETE SET NULL,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    favorite BOOLEAN DEFAULT FALSE,
    notifications_enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_project_member UNIQUE (project_id, workspace_member_id)
);

-- 12. Project Categories
CREATE TABLE project_categories (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 13. Project Labels
CREATE TABLE project_labels (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 14. Sprints
CREATE TABLE sprints (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    goal TEXT,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'PLANNED', -- PLANNED, ACTIVE, COMPLETED, CANCELLED, ARCHIVED
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    planned_story_points INTEGER DEFAULT 0,
    completed_story_points INTEGER DEFAULT 0,
    velocity DOUBLE PRECISION DEFAULT 0.0,
    capacity DOUBLE PRECISION DEFAULT 0.0,
    completed_task_count INTEGER DEFAULT 0,
    total_task_count INTEGER DEFAULT 0,
    order_index INTEGER DEFAULT 0,
    archived_at TIMESTAMP WITHOUT TIME ZONE,
    cancelled_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 15. Sprint Goals
CREATE TABLE sprint_goals (
    id BIGSERIAL PRIMARY KEY,
    sprint_id BIGINT NOT NULL REFERENCES sprints(id) ON DELETE CASCADE,
    goal_text TEXT NOT NULL,
    is_achieved BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 16. Sprint Statistics
CREATE TABLE sprint_statisticss (
    id BIGSERIAL PRIMARY KEY,
    sprint_id BIGINT NOT NULL REFERENCES sprints(id) ON DELETE CASCADE,
    total_story_points INTEGER NOT NULL DEFAULT 0,
    completed_story_points INTEGER NOT NULL DEFAULT 0,
    velocity DOUBLE PRECISION,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 17. Epics
CREATE TABLE epics (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 18. Stories
CREATE TABLE stories (
    id BIGSERIAL PRIMARY KEY,
    epic_id BIGINT REFERENCES epics(id) ON DELETE SET NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 19. Tasks
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    sprint_id BIGINT REFERENCES sprints(id) ON DELETE SET NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'TODO', -- TODO, IN_PROGRESS, IN_REVIEW, DONE
    priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, URGENT
    due_date TIMESTAMP WITHOUT TIME ZONE,
    parent_task_id BIGINT REFERENCES tasks(id) ON DELETE SET NULL,
    story_points INTEGER DEFAULT 0,
    type VARCHAR(50) NOT NULL DEFAULT 'TASK', -- TASK, BUG, FEATURE, STORY, EPIC
    epic_id BIGINT REFERENCES epics(id) ON DELETE SET NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 20. Task Assignments
CREATE TABLE task_assignments (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_task_assignment UNIQUE (task_id, user_id)
);

-- 21. Task Comments
CREATE TABLE task_comments (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES users(id),
    comment_text TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 22. Task Attachments
CREATE TABLE task_attachments (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    file_metadata_id BIGINT NOT NULL, -- Logical link to file_metadatas
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 23. Task Labels
CREATE TABLE task_labels (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    label_name VARCHAR(100) NOT NULL,
    color VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 24. Task Tags
CREATE TABLE task_tags (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    tag_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 25. Task History
CREATE TABLE task_histories (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    field_name VARCHAR(100) NOT NULL,
    old_value TEXT,
    new_value TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 26. Kanban Boards
CREATE TABLE boards (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 27. Board Columns
CREATE TABLE board_columns (
    id BIGSERIAL PRIMARY KEY,
    board_id BIGINT NOT NULL REFERENCES boards(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    wip_limit INTEGER,
    position INTEGER NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 28. Board Cards
CREATE TABLE board_cards (
    id BIGSERIAL PRIMARY KEY,
    column_id BIGINT NOT NULL REFERENCES board_columns(id) ON DELETE CASCADE,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    position INTEGER NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 29. Issues (Bugs, Features, Stories details)
CREATE TABLE issues (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    severity VARCHAR(50) NOT NULL DEFAULT 'MEDIUM', -- LOW, MEDIUM, HIGH, CRITICAL
    resolution_status VARCHAR(100),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 30. Comments (Collaboration)
CREATE TABLE comments (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES users(id),
    content TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 31. Comment Replies
CREATE TABLE comment_replies (
    id BIGSERIAL PRIMARY KEY,
    comment_id BIGINT NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES users(id),
    content TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 32. Mentions
CREATE TABLE mentions (
    id BIGSERIAL PRIMARY KEY,
    comment_id BIGINT NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 33. Emoji Reactions
CREATE TABLE reactions (
    id BIGSERIAL PRIMARY KEY,
    comment_id BIGINT NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    emoji VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_user_comment_reaction UNIQUE (comment_id, user_id, emoji)
);

-- 34. Notifications
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    recipient_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(150) NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    type VARCHAR(50) NOT NULL, -- ASSIGNMENT, MENTION, COMMENT, DEADLINE, STATUS_CHANGE
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 35. Notification Preferences
CREATE TABLE notification_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    email_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    in_app_notifications BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_user_notification_preference UNIQUE (user_id)
);

-- 36. Activity Logs
CREATE TABLE activity_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(255) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 37. Audit Logs (Detailed State tracking)
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    action VARCHAR(100) NOT NULL,
    table_name VARCHAR(100) NOT NULL,
    record_id BIGINT NOT NULL,
    old_state TEXT,
    new_state TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 38. File Metadata
CREATE TABLE file_metadatas (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    file_type VARCHAR(100),
    uploader_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 39. File Version History
CREATE TABLE file_versions (
    id BIGSERIAL PRIMARY KEY,
    file_metadata_id BIGINT NOT NULL REFERENCES file_metadatas(id) ON DELETE CASCADE,
    version_number INTEGER NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 40. Dashboard Metrics
CREATE TABLE dashboard_metricss (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    completion_percentage DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    productivity_score DOUBLE PRECISION,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 41. Sprint Metrics
CREATE TABLE sprint_metricss (
    id BIGSERIAL PRIMARY KEY,
    sprint_id BIGINT NOT NULL REFERENCES sprints(id) ON DELETE CASCADE,
    completion_percentage DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    burndown_data TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 42. Project Statistics
CREATE TABLE project_statisticss (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    total_tasks INTEGER NOT NULL DEFAULT 0,
    completed_tasks INTEGER NOT NULL DEFAULT 0,
    blocked_tasks INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 43. System Configuration
CREATE TABLE system_configurations (
    id BIGSERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 44. Admin Action Log
CREATE TABLE admin_action_logs (
    id BIGSERIAL PRIMARY KEY,
    admin_id BIGINT NOT NULL REFERENCES users(id),
    action VARCHAR(255) NOT NULL,
    target_user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    target_workspace_id BIGINT REFERENCES workspaces(id) ON DELETE SET NULL,
    reason TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- ============================================================================
-- Indexes for Performance Optimization
-- ============================================================================
CREATE INDEX idx_user_username ON users(username);
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_workspace_slug ON workspaces(slug);
CREATE INDEX idx_project_workspace ON projects(workspace_id);
CREATE INDEX idx_sprint_project ON sprints(project_id);
CREATE INDEX idx_task_sprint ON tasks(sprint_id);
CREATE INDEX idx_task_epic ON tasks(epic_id);
CREATE INDEX idx_board_column_board ON board_columns(board_id);
CREATE INDEX idx_board_card_column ON board_cards(column_id);
CREATE INDEX idx_comment_task ON comments(task_id);
CREATE INDEX idx_notification_recipient ON notifications(recipient_id);
CREATE INDEX idx_activity_user ON activity_logs(user_id);
CREATE INDEX idx_audit_record ON audit_logs(table_name, record_id);
