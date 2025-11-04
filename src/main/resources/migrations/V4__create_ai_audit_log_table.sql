CREATE TABLE IF NOT EXISTS order_service.ai_audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    timestamp TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    chat_model VARCHAR(255),
    tracking_id VARCHAR(255),
    input_tokens_count NUMERIC,
    output_tokens_count NUMERIC,
    total_tokens_count NUMERIC
);
