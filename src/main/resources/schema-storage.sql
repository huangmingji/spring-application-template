-- File storage table
CREATE TABLE IF NOT EXISTS file_storage (
    id BIGINT PRIMARY KEY,
    bucket VARCHAR(100) NOT NULL DEFAULT 'default',
    original_name VARCHAR(255) NOT NULL,
    stored_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_size BIGINT NOT NULL DEFAULT 0,
    mime_type VARCHAR(100),
    uploader_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    creator_id BIGINT NOT NULL DEFAULT 0,
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modifier_id BIGINT NOT NULL DEFAULT 0,
    last_modification_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_file_storage_uploader_id (uploader_id),
    INDEX idx_file_storage_bucket (bucket),
    INDEX idx_file_storage_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;