-- ============================================================================
-- SprintForge PostgreSQL Baseline Schema (V1)
-- Initial version representing existing database state.
-- Contains all tables with standard BaseEntity audit columns and mapping relations.
-- ============================================================================

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
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    user_agent VARCHAR(255),
    ip_address VARCHAR(100),
    last_used TIMESTAMP WITH TIME ZONE,
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
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
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
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
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
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES project_roles(id) ON DELETE SET NULL,
    joined_at TIMESTAMP WITHOUT TIME ZONE,
    added_by BIGINT,
    status VARCHAR(50) DEFAULT 'ACTIVE',
    favorite BOOLEAN DEFAULT FALSE,
    notifications_enabled BOOLEAN DEFAULT TRUE,
    allocation_percentage DOUBLE PRECISION DEFAULT 100.0,
    last_accessed TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_project_member UNIQUE (project_id, workspace_member_id)
);

-- 13. Project Categories
CREATE TABLE project_categories (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT NOT NULL REFERENCES workspaces(id) ON DELETE CASCADE,
    project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    color VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 14. Project Labels
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

-- 15. Sprints
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

-- 16. Sprint Goals
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

-- 17. Sprint Statistics
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

-- 18. Epics
CREATE TABLE epics (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    parent_epic_id BIGINT REFERENCES epics(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    description TEXT,
    color VARCHAR(50),
    status VARCHAR(50) DEFAULT 'PLANNED',
    owner_id BIGINT,
    start_date DATE,
    end_date DATE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    progress_percentage DOUBLE PRECISION DEFAULT 0.0,
    estimated_story_points INTEGER DEFAULT 0,
    completed_story_points INTEGER DEFAULT 0,
    is_archived BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 19. Stories
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

-- 20. Tasks
CREATE TABLE tasks (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE,
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

-- 21. Task Assignments
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

-- 22. Task Comments
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

-- 23. Task Attachments
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

-- 24. Task Labels
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

-- 25. Task Tags
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

-- 26. Task History
CREATE TABLE task_histories (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id),
    action_type VARCHAR(50) NOT NULL,
    field_name VARCHAR(100),
    old_value TEXT,
    new_value TEXT,
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 27. Kanban Boards
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

-- 28. Board Columns
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

-- 29. Board Cards
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

-- 30. Issues (Bugs, Features, Stories details)
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

-- 31. Comments (Collaboration)
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

-- 32. Comment Replies
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

-- 33. Mentions
CREATE TABLE mentions (
    id BIGSERIAL PRIMARY KEY,
    comment_id BIGINT NOT NULL REFERENCES comments(id) ON DELETE CASCADE,
    mentioned_user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 34. Emoji Reactions
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

-- 35. Notifications
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

-- 36. Notification Preferences
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

-- 37. Activity Logs
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
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);



-- 38. Audit Logs (Detailed State tracking)
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

-- 39. File Metadata
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

-- 40. File Version History
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

-- 41. Dashboard Metrics
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

-- 42. Sprint Metrics
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

-- 43. Project Statistics
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

-- 44. System Configuration
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

-- 45. Admin Action Log
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

-- 46. Custom Fields
CREATE TABLE custom_fields (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    field_type VARCHAR(50) NOT NULL,
    options TEXT,
    required BOOLEAN NOT NULL DEFAULT FALSE,
    default_value TEXT,
    position INTEGER DEFAULT 0,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    validation_rules TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_project_field_name UNIQUE (project_id, name)
);

-- 47. Custom Field Values
CREATE TABLE custom_field_values (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL REFERENCES tasks(id) ON DELETE CASCADE,
    custom_field_id BIGINT NOT NULL REFERENCES custom_fields(id) ON DELETE CASCADE,
    field_value TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0,
    CONSTRAINT uq_task_field UNIQUE (task_id, custom_field_id)
);

-- 48. Workflows
CREATE TABLE IF NOT EXISTS workflows (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT REFERENCES workspaces(id) ON DELETE CASCADE,
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
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 49. Workflow Versions
CREATE TABLE IF NOT EXISTS workflow_versions (
    id BIGSERIAL PRIMARY KEY,
    workflow_id BIGINT NOT NULL REFERENCES workflows(id) ON DELETE CASCADE,
    version_number INT NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    published_at TIMESTAMP WITHOUT TIME ZONE,
    change_summary TEXT,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 50. Workflow Executions
CREATE TABLE IF NOT EXISTS workflow_executions (
    id BIGSERIAL PRIMARY KEY,
    workflow_id BIGINT REFERENCES workflows(id) ON DELETE CASCADE,
    workflow_version_id BIGINT REFERENCES workflow_versions(id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    triggered_by VARCHAR(255),
    trigger_source VARCHAR(255),
    started_at TIMESTAMP WITHOUT TIME ZONE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    duration BIGINT,
    current_node_id BIGINT,
    context_id VARCHAR(255),
    result TEXT,
    error TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 51. Dashboard Widgets
CREATE TABLE IF NOT EXISTS dashboard_widgets (
    id BIGSERIAL PRIMARY KEY,
    dashboard_id BIGINT NOT NULL,
    widget_type VARCHAR(100) NOT NULL,
    title VARCHAR(255),
    configuration TEXT,
    position INTEGER DEFAULT 0,
    width INTEGER DEFAULT 12,
    height INTEGER DEFAULT 6,
    refresh_interval INTEGER DEFAULT 300,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 52. Saved Views
CREATE TABLE IF NOT EXISTS saved_views (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    project_id BIGINT,
    user_id BIGINT,
    name VARCHAR(255) NOT NULL,
    view_type VARCHAR(100),
    filters TEXT,
    sorting TEXT,
    grouping TEXT,
    columns TEXT,
    visibility VARCHAR(255),
    is_shared BOOLEAN DEFAULT FALSE,
    is_favorite BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 53. Task Notifications
CREATE TABLE IF NOT EXISTS task_notifications (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT,
    workspace_id BIGINT,
    project_id BIGINT,
    recipient_id BIGINT,
    actor_id BIGINT,
    notification_type VARCHAR(100),
    title VARCHAR(255),
    message TEXT,
    priority VARCHAR(255),
    action_url VARCHAR(255),
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    read_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 54. Project Settings
CREATE TABLE IF NOT EXISTS project_settings (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT UNIQUE,
    default_sprint_length_days INTEGER DEFAULT 14,
    working_days VARCHAR(255),
    business_hours VARCHAR(255),
    timezone VARCHAR(255) DEFAULT 'UTC',
    story_point_scale VARCHAR(255),
    estimate_unit VARCHAR(255),
    notification_defaults BOOLEAN DEFAULT FALSE,
    visibility_defaults VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 55. Project Decisions
CREATE TABLE IF NOT EXISTS project_decisions (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    decision TEXT,
    reason TEXT,
    decision_maker_id BIGINT,
    impact VARCHAR(255),
    problem_statement TEXT,
    alternatives TEXT,
    reasoning TEXT,
    status VARCHAR(255),
    decision_date DATE,
    owner_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 56. Project Documents
CREATE TABLE IF NOT EXISTS project_documents (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    file_id BIGINT,
    uploaded_by BIGINT,
    document_version INTEGER DEFAULT 1,
    folder_id BIGINT,
    slug VARCHAR(255),
    content TEXT,
    format VARCHAR(255),
    version_number INTEGER DEFAULT 1,
    author_id BIGINT,
    last_editor_id BIGINT,
    is_pinned BOOLEAN DEFAULT FALSE,
    is_favorite BOOLEAN DEFAULT FALSE,
    is_archived BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 57. Project Risks
CREATE TABLE IF NOT EXISTS project_risks (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(255) DEFAULT 'TECHNICAL',
    severity VARCHAR(100) DEFAULT 'MEDIUM',
    risk_score INTEGER DEFAULT 4,
    probability VARCHAR(100) DEFAULT 'MEDIUM',
    impact VARCHAR(100) DEFAULT 'MEDIUM',
    status VARCHAR(100) DEFAULT 'IDENTIFIED',
    owner_id BIGINT,
    identified_date DATE,
    target_mitigation_date DATE,
    resolved_date TIMESTAMP WITHOUT TIME ZONE,
    mitigation_plan TEXT,
    contingency_plan TEXT,
    trigger_conditions TEXT,
    is_archived BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 58. Key Results
CREATE TABLE IF NOT EXISTS key_results (
    id BIGSERIAL PRIMARY KEY,
    objective_id BIGINT,
    goal_id BIGINT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    metric_type VARCHAR(255) DEFAULT 'PERCENTAGE',
    initial_value DOUBLE PRECISION DEFAULT 0.0,
    target_value DOUBLE PRECISION DEFAULT 100.0,
    current_value DOUBLE PRECISION DEFAULT 0.0,
    unit VARCHAR(50),
    weight DOUBLE PRECISION DEFAULT 1.0,
    progress_percentage DOUBLE PRECISION DEFAULT 0.0,
    status VARCHAR(100) DEFAULT 'IN_PROGRESS',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 59. Recurring Tasks
CREATE TABLE IF NOT EXISTS recurring_tasks (
    id BIGSERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL DEFAULT 1,
    workspace_id BIGINT,
    project_id BIGINT,
    template_task_id BIGINT,
    title VARCHAR(255),
    frequency VARCHAR(50) NOT NULL DEFAULT 'DAILY',
    interval_value INTEGER DEFAULT 1,
    days_of_week TEXT,
    day_of_month INTEGER,
    month_of_year INTEGER,
    cron_expression VARCHAR(255),
    start_date DATE,
    end_date DATE,
    max_occurrences INTEGER,
    generated_occurrences INTEGER DEFAULT 0,
    last_execution TIMESTAMP WITHOUT TIME ZONE,
    next_execution TIMESTAMP WITHOUT TIME ZONE,
    timezone VARCHAR(255) DEFAULT 'UTC',
    paused BOOLEAN DEFAULT FALSE,
    enabled BOOLEAN DEFAULT TRUE,
    skip_weekends BOOLEAN DEFAULT FALSE,
    skip_holidays BOOLEAN DEFAULT FALSE,
    auto_assign BOOLEAN DEFAULT FALSE,
    auto_notify BOOLEAN DEFAULT FALSE,
    created_by_user_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 60. Task Templates
CREATE TABLE IF NOT EXISTS task_templates (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    project_id BIGINT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    title_pattern VARCHAR(255),
    default_priority VARCHAR(100),
    default_estimate_hours DOUBLE PRECISION,
    is_public BOOLEAN DEFAULT FALSE,
    is_archived BOOLEAN DEFAULT FALSE,
    usage_count INTEGER DEFAULT 0,
    favorited_count INTEGER DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 61. Workspace API Tokens
CREATE TABLE IF NOT EXISTS workspace_api_tokens (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    name VARCHAR(255) NOT NULL,
    token_hash VARCHAR(255) NOT NULL UNIQUE,
    permissions TEXT,
    created_by_id BIGINT,
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    last_used_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 62. Workspace Audit Logs
CREATE TABLE IF NOT EXISTS workspace_audit_logs (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    actor_id BIGINT,
    action VARCHAR(255) NOT NULL,
    entity_type VARCHAR(100),
    entity_id BIGINT,
    old_value TEXT,
    new_value TEXT,
    ip_address VARCHAR(100),
    user_agent VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 63. Workspace Preferences
CREATE TABLE IF NOT EXISTS workspace_preferences (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT NOT NULL,
    user_id BIGINT,
    theme VARCHAR(50) DEFAULT 'LIGHT',
    email_notifications BOOLEAN DEFAULT TRUE,
    push_notifications BOOLEAN DEFAULT TRUE,
    in_app_notifications BOOLEAN DEFAULT TRUE,
    sidebar_collapsed BOOLEAN DEFAULT FALSE,
    timezone VARCHAR(255) DEFAULT 'UTC',
    language VARCHAR(255) DEFAULT 'en',
    date_format VARCHAR(255) DEFAULT 'YYYY-MM-DD',
    time_format VARCHAR(255) DEFAULT '24h',
    week_starts_on VARCHAR(255) DEFAULT 'MONDAY',
    working_days TEXT,
    business_hours_start VARCHAR(255) DEFAULT '09:00',
    business_hours_end VARCHAR(255) DEFAULT '18:00',
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 64. Workspace Webhooks
CREATE TABLE IF NOT EXISTS workspace_webhooks (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(500) NOT NULL,
    secret VARCHAR(255),
    events TEXT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    failure_count INTEGER DEFAULT 0,
    last_delivery_at TIMESTAMP WITHOUT TIME ZONE,
    last_delivery_status VARCHAR(255),
    created_by_id BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 65. Webhook Deliveries
CREATE TABLE IF NOT EXISTS webhook_deliveries (
    id BIGSERIAL PRIMARY KEY,
    webhook_id BIGINT,
    workspace_id BIGINT,
    event_type VARCHAR(100),
    payload TEXT,
    response_code INTEGER,
    response_body TEXT,
    duration_ms BIGINT,
    is_success BOOLEAN DEFAULT FALSE,
    attempt_number INTEGER DEFAULT 1,
    error_message TEXT,
    delivered_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 66. User Preferences
CREATE TABLE IF NOT EXISTS user_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE,
    theme VARCHAR(50) DEFAULT 'LIGHT',
    language VARCHAR(50) DEFAULT 'en',
    timezone VARCHAR(100) DEFAULT 'UTC',
    email_notifications BOOLEAN DEFAULT TRUE,
    in_app_notifications BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 67. Workspace Settings
CREATE TABLE IF NOT EXISTS workspace_settings (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT UNIQUE,
    allow_public_projects BOOLEAN DEFAULT FALSE,
    require_2fa BOOLEAN DEFAULT FALSE,
    session_timeout_minutes INTEGER DEFAULT 1440,
    allowed_email_domains TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 68. Labels
CREATE TABLE IF NOT EXISTS labels (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50) DEFAULT '#0052CC',
    description VARCHAR(255),
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

-- 69. Task Labels Mapping
CREATE TABLE IF NOT EXISTS task_labels_mapping (
    task_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, label_id)
);

-- 70. AI Prompt Histories
CREATE TABLE IF NOT EXISTS ai_prompt_histories (
    id BIGSERIAL PRIMARY KEY,
    workspace_id BIGINT NOT NULL,
    user_id BIGINT,
    prompt TEXT,
    response TEXT,
    model_name VARCHAR(100),
    tokens_used INTEGER,
    execution_time_ms BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_by VARCHAR(255),
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
CREATE INDEX idx_custom_field_project ON custom_fields(project_id);
CREATE INDEX idx_custom_field_value_task ON custom_field_values(task_id);
