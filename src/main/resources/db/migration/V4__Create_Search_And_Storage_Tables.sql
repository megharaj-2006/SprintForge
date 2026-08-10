-- Cluster 3 Search and Storage Platform Migration

CREATE TABLE IF NOT EXISTS platform_search_index (
    id BIGSERIAL PRIMARY KEY,
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100) NOT NULL,
    workspace_id VARCHAR(100),
    project_id VARCHAR(100),
    title VARCHAR(255) NOT NULL,
    content TEXT,
    keywords TEXT,
    checksum VARCHAR(100),
    version INTEGER DEFAULT 1 NOT NULL,
    last_indexed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    is_archived BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE INDEX idx_psi_entity ON platform_search_index(entity_type, entity_id);
CREATE INDEX idx_psi_workspace ON platform_search_index(workspace_id);
CREATE INDEX idx_psi_project ON platform_search_index(project_id);

CREATE TABLE IF NOT EXISTS platform_search_history (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    workspace_id VARCHAR(100),
    query_text VARCHAR(255) NOT NULL,
    filters TEXT,
    entity_types VARCHAR(500),
    execution_time_ms BIGINT NOT NULL,
    result_count INTEGER NOT NULL,
    searched_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_psh_user ON platform_search_history(user_id);
CREATE INDEX idx_psh_query ON platform_search_history(query_text);

CREATE TABLE IF NOT EXISTS platform_saved_searches (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    workspace_id VARCHAR(100),
    name VARCHAR(150) NOT NULL,
    query_text VARCHAR(255),
    filters TEXT,
    is_shared BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_pss_user ON platform_saved_searches(user_id);

CREATE TABLE IF NOT EXISTS platform_stored_files (
    id BIGSERIAL PRIMARY KEY,
    workspace_id VARCHAR(100),
    project_id VARCHAR(100),
    owner_id VARCHAR(100) NOT NULL,
    original_name VARCHAR(255) NOT NULL,
    stored_name VARCHAR(255) NOT NULL,
    mime_type VARCHAR(100) NOT NULL,
    extension VARCHAR(30) NOT NULL,
    size_bytes BIGINT NOT NULL,
    checksum VARCHAR(100) NOT NULL,
    storage_provider VARCHAR(50) DEFAULT 'LOCAL' NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    visibility VARCHAR(30) DEFAULT 'PRIVATE' NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE,
    uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_psf_workspace ON platform_stored_files(workspace_id);
CREATE INDEX idx_psf_owner ON platform_stored_files(owner_id);
CREATE INDEX idx_psf_checksum ON platform_stored_files(checksum);

CREATE TABLE IF NOT EXISTS platform_file_versions (
    id BIGSERIAL PRIMARY KEY,
    file_id BIGINT NOT NULL,
    version_number INTEGER NOT NULL,
    uploaded_by VARCHAR(100) NOT NULL,
    checksum VARCHAR(100) NOT NULL,
    size_bytes BIGINT NOT NULL,
    storage_path VARCHAR(500) NOT NULL,
    uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_pfv_file FOREIGN KEY (file_id) REFERENCES platform_stored_files(id) ON DELETE CASCADE
);

CREATE INDEX idx_pfv_file ON platform_file_versions(file_id);

CREATE TABLE IF NOT EXISTS platform_file_metadata (
    id BIGSERIAL PRIMARY KEY,
    file_id BIGINT NOT NULL,
    meta_key VARCHAR(100) NOT NULL,
    meta_value TEXT NOT NULL,
    CONSTRAINT fk_pfm_file FOREIGN KEY (file_id) REFERENCES platform_stored_files(id) ON DELETE CASCADE
);

CREATE INDEX idx_pfm_file ON platform_file_metadata(file_id);
