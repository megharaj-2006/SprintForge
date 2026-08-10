-- Communication Platform Migration

CREATE TABLE IF NOT EXISTS communication_notifications (
    id BIGSERIAL PRIMARY KEY,
    recipient_id VARCHAR(100) NOT NULL,
    actor_id VARCHAR(100),
    workspace_id VARCHAR(100),
    project_id VARCHAR(100),
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100) NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    priority VARCHAR(30) DEFAULT 'NORMAL' NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    channel VARCHAR(50) DEFAULT 'IN_APP' NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING' NOT NULL,
    action_url VARCHAR(500),
    is_read BOOLEAN DEFAULT FALSE NOT NULL,
    read_at TIMESTAMP WITH TIME ZONE,
    is_archived BOOLEAN DEFAULT FALSE NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_comm_notif_recipient ON communication_notifications(recipient_id);
CREATE INDEX idx_comm_notif_read ON communication_notifications(recipient_id, is_read);
CREATE INDEX idx_comm_notif_workspace ON communication_notifications(workspace_id);

CREATE TABLE IF NOT EXISTS communication_notification_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    workspace_id VARCHAR(100),
    channel VARCHAR(50) NOT NULL,
    notification_type VARCHAR(50) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE NOT NULL,
    mute_until TIMESTAMP WITH TIME ZONE,
    digest_frequency VARCHAR(30) DEFAULT 'IMMEDIATE' NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_comm_notif_pref_user ON communication_notification_preferences(user_id);
CREATE INDEX idx_comm_notif_pref_workspace ON communication_notification_preferences(workspace_id);

CREATE TABLE IF NOT EXISTS communication_activity_feed (
    id BIGSERIAL PRIMARY KEY,
    workspace_id VARCHAR(100),
    project_id VARCHAR(100),
    actor_id VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100) NOT NULL,
    activity_type VARCHAR(50) NOT NULL,
    summary TEXT NOT NULL,
    metadata TEXT,
    visibility VARCHAR(30) DEFAULT 'PUBLIC' NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_act_feed_workspace ON communication_activity_feed(workspace_id);
CREATE INDEX idx_act_feed_project ON communication_activity_feed(project_id);
CREATE INDEX idx_act_feed_actor ON communication_activity_feed(actor_id);
CREATE INDEX idx_act_feed_entity ON communication_activity_feed(entity_type, entity_id);

CREATE TABLE IF NOT EXISTS email_templates (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    subject VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    variables TEXT,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    version INT DEFAULT 1 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS email_queue (
    id BIGSERIAL PRIMARY KEY,
    recipient VARCHAR(255) NOT NULL,
    template_name VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING' NOT NULL,
    attempts INT DEFAULT 0 NOT NULL,
    scheduled_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    sent_at TIMESTAMP WITH TIME ZONE,
    error TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_email_queue_status ON email_queue(status, scheduled_at);

CREATE TABLE IF NOT EXISTS communication_webhook_deliveries (
    id BIGSERIAL PRIMARY KEY,
    webhook_url VARCHAR(500) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING' NOT NULL,
    response_code INT,
    attempts INT DEFAULT 0 NOT NULL,
    duration_ms BIGINT DEFAULT 0 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS communication_templates (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    channel VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    subject VARCHAR(255),
    content TEXT NOT NULL,
    variables TEXT,
    version INT DEFAULT 1 NOT NULL,
    active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);
