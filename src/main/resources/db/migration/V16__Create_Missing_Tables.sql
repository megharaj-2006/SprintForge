-- ============================================================================
-- SprintForge PostgreSQL Migration V16: Create Missing Tables
-- Creates all tables that are defined as JPA entities but not yet in the DB.
-- All tables use CREATE TABLE IF NOT EXISTS for idempotency.
-- ============================================================================

-- 1. Global Notifications (empty entity - just audit columns)
CREATE TABLE IF NOT EXISTS global_notifications (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255)
);

-- 2. Global Notification Preferences (empty entity - just audit columns)
CREATE TABLE IF NOT EXISTS global_notification_preferences (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255)
);

-- 3. Advanced Task Relationships
CREATE TABLE IF NOT EXISTS advanced_task_relationships (
    id                    BIGSERIAL PRIMARY KEY,
    version               BIGINT DEFAULT 0,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    created_by            VARCHAR(255),
    updated_by            VARCHAR(255),
    is_deleted            BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    deleted_by            VARCHAR(255),
    source_task_id        BIGINT NOT NULL,
    target_task_id        BIGINT NOT NULL,
    relationship_type     VARCHAR(255) NOT NULL,
    created_by_user_id    BIGINT
);

-- 4. Business Holidays
CREATE TABLE IF NOT EXISTS business_holidays (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    workspace_id      BIGINT NOT NULL,
    name              VARCHAR(255) NOT NULL,
    date              DATE NOT NULL,
    is_recurring      BOOLEAN DEFAULT FALSE,
    type              VARCHAR(50),
    description       VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_holiday_workspace_date ON business_holidays(workspace_id, date);

-- 5. Departments
CREATE TABLE IF NOT EXISTS departments (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    workspace_id      BIGINT NOT NULL,
    name              VARCHAR(255) NOT NULL,
    description       TEXT,
    head_member_id    BIGINT,
    color             VARCHAR(255),
    is_archived       BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_department_workspace_id ON departments(workspace_id);

-- 6. Document Folders
CREATE TABLE IF NOT EXISTS document_folders (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    project_id        BIGINT NOT NULL,
    name              VARCHAR(255) NOT NULL,
    parent_id         BIGINT
);

-- 7. Document Versions
CREATE TABLE IF NOT EXISTS document_versions (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    document_id       BIGINT NOT NULL,
    version_number    INT NOT NULL,
    content           TEXT,
    change_summary    VARCHAR(255)
);

-- 8. Goals
CREATE TABLE IF NOT EXISTS goals (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    project_id        BIGINT NOT NULL,
    title             VARCHAR(255) NOT NULL,
    description       TEXT,
    owner_id          BIGINT,
    priority          VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    status            VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    start_date        DATE,
    target_date       DATE,
    completed_date    TIMESTAMP WITHOUT TIME ZONE,
    weight            DOUBLE PRECISION DEFAULT 1.0,
    is_archived       BOOLEAN DEFAULT FALSE
);

-- 9. Inbox Items
CREATE TABLE IF NOT EXISTS inbox_items (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    user_id           BIGINT NOT NULL,
    task_id           BIGINT,
    type              VARCHAR(100) NOT NULL,
    title             VARCHAR(255) NOT NULL,
    summary           TEXT,
    is_read           BOOLEAN DEFAULT FALSE,
    is_archived       BOOLEAN DEFAULT FALSE,
    snoozed_until     TIMESTAMP WITHOUT TIME ZONE
);

-- 10. Objectives
CREATE TABLE IF NOT EXISTS objectives (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    goal_id           BIGINT NOT NULL,
    title             VARCHAR(255) NOT NULL,
    description       TEXT,
    owner_id          BIGINT,
    status            VARCHAR(50) NOT NULL DEFAULT 'NOT_STARTED',
    weight            DOUBLE PRECISION DEFAULT 1.0,
    start_date        DATE,
    target_date       DATE
);

-- 11. Portfolio Projects
CREATE TABLE IF NOT EXISTS portfolio_projects (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    portfolio_id      BIGINT NOT NULL,
    project_id        BIGINT NOT NULL
);

-- 12. Portfolios
CREATE TABLE IF NOT EXISTS portfolios (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    workspace_id      BIGINT NOT NULL,
    name              VARCHAR(255) NOT NULL,
    description       TEXT,
    owner_id          BIGINT,
    status            VARCHAR(50) DEFAULT 'ACTIVE'
);

-- 13. Project Approvals
CREATE TABLE IF NOT EXISTS project_approvals (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    project_id        BIGINT NOT NULL,
    entity_type       VARCHAR(100) NOT NULL,
    entity_id         BIGINT NOT NULL,
    title             VARCHAR(255) NOT NULL,
    status            VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    requested_by_id   BIGINT,
    requested_at      TIMESTAMP WITHOUT TIME ZONE,
    approved_by_id    BIGINT,
    approved_at       TIMESTAMP WITHOUT TIME ZONE,
    comments          TEXT
);

-- 14. Project Changes
CREATE TABLE IF NOT EXISTS project_changes (
    id                    BIGSERIAL PRIMARY KEY,
    version               BIGINT DEFAULT 0,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    created_by            VARCHAR(255),
    updated_by            VARCHAR(255),
    is_deleted            BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    deleted_by            VARCHAR(255),
    project_id            BIGINT NOT NULL,
    change_type           VARCHAR(50) NOT NULL DEFAULT 'SCOPE',
    title                 VARCHAR(255) NOT NULL,
    description           TEXT,
    reason                TEXT,
    impact                TEXT,
    requested_by_id       BIGINT,
    approved_by_id        BIGINT,
    implemented_by_id     BIGINT,
    status                VARCHAR(50) NOT NULL DEFAULT 'REQUESTED',
    completed_at          TIMESTAMP WITHOUT TIME ZONE
);

-- 15. Recently Viewed Items
CREATE TABLE IF NOT EXISTS recently_viewed_items (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    user_id           BIGINT NOT NULL,
    entity_type       VARCHAR(100) NOT NULL,
    entity_id         BIGINT NOT NULL,
    title             VARCHAR(255),
    last_viewed_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

-- 16. Releases
CREATE TABLE IF NOT EXISTS releases (
    id                      BIGSERIAL PRIMARY KEY,
    version                 BIGINT DEFAULT 0,
    created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP WITHOUT TIME ZONE,
    created_by              VARCHAR(255),
    updated_by              VARCHAR(255),
    is_deleted              BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at              TIMESTAMP WITHOUT TIME ZONE,
    deleted_by              VARCHAR(255),
    project_id              BIGINT NOT NULL,
    name                    VARCHAR(255) NOT NULL,
    release_version         VARCHAR(100) NOT NULL,
    description             TEXT,
    release_type            VARCHAR(50) NOT NULL DEFAULT 'MINOR',
    status                  VARCHAR(50) NOT NULL DEFAULT 'PLANNING',
    planned_start           DATE,
    planned_release_date    DATE,
    actual_release_date     DATE,
    owner_id                BIGINT,
    release_notes           TEXT,
    color                   VARCHAR(50) DEFAULT '#3B82F6'
);

-- 17. Report Executions
CREATE TABLE IF NOT EXISTS report_executions (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    report_id         BIGINT NOT NULL,
    executed_at       TIMESTAMP WITHOUT TIME ZONE,
    status            VARCHAR(50) DEFAULT 'COMPLETED',
    download_url      VARCHAR(500)
);

-- 18. Saved Reports
CREATE TABLE IF NOT EXISTS saved_reports (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    project_id        BIGINT NOT NULL,
    name              VARCHAR(255) NOT NULL,
    report_type       VARCHAR(100) NOT NULL DEFAULT 'EXECUTIVE_SUMMARY',
    format            VARCHAR(50) NOT NULL DEFAULT 'PDF',
    parameters        TEXT
);

-- 19. Task Estimates
CREATE TABLE IF NOT EXISTS task_estimates (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    task_id           BIGINT NOT NULL,
    estimate_type     VARCHAR(100) NOT NULL,
    estimated_value   DOUBLE PRECISION NOT NULL,
    actual_value      DOUBLE PRECISION,
    variance          DOUBLE PRECISION,
    estimated_by      BIGINT
);

-- 20. Task Permission Overrides
CREATE TABLE IF NOT EXISTS task_permission_overrides (
    id                    BIGSERIAL PRIMARY KEY,
    version               BIGINT DEFAULT 0,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    created_by            VARCHAR(255),
    updated_by            VARCHAR(255),
    is_deleted            BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    deleted_by            VARCHAR(255),
    task_id               BIGINT NOT NULL,
    user_id               BIGINT NOT NULL,
    permission            VARCHAR(100) NOT NULL,
    allowed               BOOLEAN NOT NULL DEFAULT TRUE,
    created_by_user_id    BIGINT
);

-- 21. Task Template Attachments
CREATE TABLE IF NOT EXISTS task_template_attachments (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(255),
    task_template_id    BIGINT NOT NULL,
    file_name           VARCHAR(255) NOT NULL,
    file_url            VARCHAR(500) NOT NULL,
    file_size           BIGINT,
    file_type           VARCHAR(100)
);

-- 22. Task Template Checklists
CREATE TABLE IF NOT EXISTS task_template_checklists (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(255),
    task_template_id    BIGINT NOT NULL,
    title               VARCHAR(255) NOT NULL,
    position            INT
);

-- 23. Task Template Checklist Items
CREATE TABLE IF NOT EXISTS task_template_checklist_items (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    checklist_id      BIGINT NOT NULL,
    title             VARCHAR(255) NOT NULL,
    description       TEXT,
    position          INT
);

-- 24. Task Template Custom Fields
CREATE TABLE IF NOT EXISTS task_template_custom_fields (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(255),
    task_template_id    BIGINT NOT NULL,
    custom_field_id     BIGINT NOT NULL,
    field_name          VARCHAR(255),
    field_value         TEXT
);

-- 25. Task Template Favorites
CREATE TABLE IF NOT EXISTS task_template_favorites (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(255),
    user_id             BIGINT NOT NULL,
    task_template_id    BIGINT NOT NULL,
    UNIQUE (user_id, task_template_id)
);

-- 26. Task Template Labels
CREATE TABLE IF NOT EXISTS task_template_labels (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(255),
    task_template_id    BIGINT NOT NULL,
    label_id            BIGINT NOT NULL,
    name                VARCHAR(255),
    color               VARCHAR(50)
);

-- 27. Task Template Watchers
CREATE TABLE IF NOT EXISTS task_template_watchers (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    is_deleted          BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    deleted_by          VARCHAR(255),
    task_template_id    BIGINT NOT NULL,
    user_id             BIGINT NOT NULL
);

-- 28. Task Versions
CREATE TABLE IF NOT EXISTS task_versions (
    id                    BIGSERIAL PRIMARY KEY,
    version               BIGINT DEFAULT 0,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    created_by            VARCHAR(255),
    updated_by            VARCHAR(255),
    is_deleted            BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    deleted_by            VARCHAR(255),
    task_id               BIGINT NOT NULL,
    version_number        INT NOT NULL,
    snapshot_json         TEXT NOT NULL,
    created_by_user_id    BIGINT
);

-- 29. Teams
CREATE TABLE IF NOT EXISTS teams (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    is_deleted        BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at        TIMESTAMP WITHOUT TIME ZONE,
    deleted_by        VARCHAR(255),
    workspace_id      BIGINT NOT NULL,
    department_id     BIGINT,
    name              VARCHAR(255) NOT NULL,
    description       TEXT,
    color             VARCHAR(50),
    icon              VARCHAR(255),
    lead_member_id    BIGINT,
    parent_team_id    BIGINT,
    created_by_id     BIGINT,
    is_archived       BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_team_workspace_id ON teams(workspace_id);
CREATE INDEX IF NOT EXISTS idx_team_department_id ON teams(department_id);

-- 30. Team Members
CREATE TABLE IF NOT EXISTS team_members (
    id                      BIGSERIAL PRIMARY KEY,
    version                 BIGINT DEFAULT 0,
    created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP WITHOUT TIME ZONE,
    created_by              VARCHAR(255),
    updated_by              VARCHAR(255),
    is_deleted              BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at              TIMESTAMP WITHOUT TIME ZONE,
    deleted_by              VARCHAR(255),
    team_id                 BIGINT NOT NULL,
    member_id               BIGINT NOT NULL,
    role                    VARCHAR(50) NOT NULL,
    allocation_percentage   INT,
    joined_at               TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT uk_team_member UNIQUE (team_id, member_id)
);

-- 31. Trash Records
CREATE TABLE IF NOT EXISTS trash_records (
    id                    BIGSERIAL PRIMARY KEY,
    version               BIGINT DEFAULT 0,
    created_at            TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at            TIMESTAMP WITHOUT TIME ZONE,
    created_by            VARCHAR(255),
    updated_by            VARCHAR(255),
    is_deleted            BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at            TIMESTAMP WITHOUT TIME ZONE,
    deleted_by            VARCHAR(255),
    entity_type           VARCHAR(100) NOT NULL,
    entity_id             BIGINT NOT NULL,
    deleted_by_user_id    BIGINT,
    deleted_at_record     TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    scheduled_purge_at    TIMESTAMP WITHOUT TIME ZONE,
    reason                VARCHAR(255)
);

-- 32. Trusted Devices (extends AuditableEntity - no soft delete cols)
CREATE TABLE IF NOT EXISTS trusted_devices (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    workspace_id        BIGINT NOT NULL,
    user_id             BIGINT NOT NULL,
    device_fingerprint  VARCHAR(255) NOT NULL,
    device_name         VARCHAR(255),
    device_type         VARCHAR(100),
    trusted_at          TIMESTAMP WITHOUT TIME ZONE,
    last_seen_at        TIMESTAMP WITHOUT TIME ZONE,
    is_active           BOOLEAN DEFAULT TRUE,
    UNIQUE (workspace_id, user_id, device_fingerprint)
);

-- 33. User Capacities
CREATE TABLE IF NOT EXISTS user_capacities (
    id                      BIGSERIAL PRIMARY KEY,
    version                 BIGINT DEFAULT 0,
    created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP WITHOUT TIME ZONE,
    created_by              VARCHAR(255),
    updated_by              VARCHAR(255),
    is_deleted              BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at              TIMESTAMP WITHOUT TIME ZONE,
    deleted_by              VARCHAR(255),
    user_id                 BIGINT NOT NULL UNIQUE,
    daily_hours_capacity    DOUBLE PRECISION DEFAULT 8.0,
    weekly_hours_capacity   DOUBLE PRECISION DEFAULT 40.0,
    is_on_vacation          BOOLEAN DEFAULT FALSE
);

-- 34. Workspace Brandings (extends AuditableEntity - no soft delete cols)
CREATE TABLE IF NOT EXISTS workspace_brandings (
    id                BIGSERIAL PRIMARY KEY,
    version           BIGINT DEFAULT 0,
    created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    created_by        VARCHAR(255),
    updated_by        VARCHAR(255),
    workspace_id      BIGINT NOT NULL UNIQUE,
    logo              VARCHAR(500),
    banner            VARCHAR(500),
    icon              VARCHAR(500),
    theme             VARCHAR(50),
    accent_color      VARCHAR(50),
    primary_color     VARCHAR(50),
    font_family       VARCHAR(100)
);

-- 35. Workspace Sessions (extends AuditableEntity - no soft delete cols)
CREATE TABLE IF NOT EXISTS workspace_sessions (
    id                  BIGSERIAL PRIMARY KEY,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP WITHOUT TIME ZONE,
    created_by          VARCHAR(255),
    updated_by          VARCHAR(255),
    workspace_id        BIGINT NOT NULL,
    user_id             BIGINT NOT NULL,
    session_token       VARCHAR(512) NOT NULL UNIQUE,
    ip_address          VARCHAR(50),
    user_agent          TEXT,
    device_type         VARCHAR(100),
    location            VARCHAR(255),
    is_active           BOOLEAN DEFAULT TRUE,
    last_activity_at    TIMESTAMP WITHOUT TIME ZONE,
    expires_at          TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_session_workspace_user ON workspace_sessions(workspace_id, user_id);
CREATE INDEX IF NOT EXISTS idx_session_token ON workspace_sessions(session_token);
