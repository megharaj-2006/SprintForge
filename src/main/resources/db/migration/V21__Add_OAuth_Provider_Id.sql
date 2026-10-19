-- V21: Add OAuth Provider ID column and index to users table
ALTER TABLE users ADD COLUMN IF NOT EXISTS provider_id VARCHAR(255);
ALTER TABLE users ALTER COLUMN password_hash DROP NOT NULL;
CREATE INDEX IF NOT EXISTS idx_users_provider_provider_id ON users(provider, provider_id);
