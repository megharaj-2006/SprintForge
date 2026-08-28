-- Flyway Migration V19: Add Indexes for Background Processing Reliability & Atomic Claiming

CREATE INDEX IF NOT EXISTS idx_email_queue_claim ON email_queue(status, scheduled_at);
CREATE INDEX IF NOT EXISTS idx_event_outbox_claim ON event_outbox(status, created_at);
CREATE INDEX IF NOT EXISTS idx_platform_jobs_claim ON platform_jobs(status, priority DESC, created_at ASC);
CREATE INDEX IF NOT EXISTS idx_platform_jobs_stale ON platform_jobs(status, started_at);
