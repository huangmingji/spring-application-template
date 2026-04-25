-- Message records table
CREATE TABLE IF NOT EXISTS message_records (
    id BIGINT PRIMARY KEY,
    message_type VARCHAR(20) NOT NULL,
    recipient VARCHAR(255) NOT NULL,
    subject VARCHAR(255),
    content TEXT,
    template_id VARCHAR(100),
    template_params TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    send_time DATETIME,
    error_message TEXT,
    creator_id BIGINT NOT NULL DEFAULT 0,
    creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modifier_id BIGINT NOT NULL DEFAULT 0,
    last_modification_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_message_records_recipient (recipient),
    INDEX idx_message_records_message_type (message_type),
    INDEX idx_message_records_status (status),
    INDEX idx_message_records_creation_time (creation_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Message templates table
CREATE TABLE IF NOT EXISTS message_templates (
    id BIGINT PRIMARY KEY,
    template_code VARCHAR(100) NOT NULL UNIQUE,
    template_name VARCHAR(255) NOT NULL,
    message_type VARCHAR(20) NOT NULL,
    subject VARCHAR(255),
    content TEXT,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    description VARCHAR(500),
    UNIQUE KEY uk_template_code (template_code),
    INDEX idx_message_templates_type (message_type),
    INDEX idx_message_templates_enabled (enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;