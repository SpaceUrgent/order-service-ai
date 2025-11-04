package io.spaceurgent.order.service.ai.infrastructure.db.impl;

import io.spaceurgent.order.service.ai.infrastructure.ai.audit.AiAuditLog;
import io.spaceurgent.order.service.ai.infrastructure.ai.audit.AiAuditLogRepository;
import io.spaceurgent.order.service.ai.infrastructure.ai.audit.ChatModelUsageSummary;
import io.spaceurgent.order.service.ai.infrastructure.db.mapper.ChatModelUsageSummaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuditLogRepositoryImpl implements AiAuditLogRepository {
    private static final String INSERT_AUDIT_LOG_SQL = """
            INSERT INTO order_service.ai_audit_logs
                (timestamp, chat_model, tracking_id, input_tokens_count, output_tokens_count, total_tokens_count)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    private static final String SELECT_CHAT_MODEL_SUMMARY_SQL = """
            SELECT
                chat_model,
                COUNT(*) AS request_total,
                COALESCE(SUM(input_tokens_count), 0) AS input_tokens_total,
                COALESCE(SUM(output_tokens_count), 0) AS output_tokens_total,
                COALESCE(SUM(total_tokens_count), 0) AS tokens_total
            FROM order_service.ai_audit_logs
            GROUP BY chat_model
            ORDER BY tokens_total desc
            """;

    private final JdbcTemplate jdbcTemplate;
    private final ChatModelUsageSummaryMapper chatModelUsageSummaryMapper;

    @Override
    public void save(AiAuditLog auditLog) {
        assert auditLog != null : "Audit log is required";
        jdbcTemplate.update(insertAuditLogPreparedStatementCreator(auditLog));
    }

    @Override
    public List<ChatModelUsageSummary> getChatModelUsageSummary() {
        return jdbcTemplate.query(SELECT_CHAT_MODEL_SUMMARY_SQL, chatModelUsageSummaryMapper);
    }

    private static PreparedStatementCreator insertAuditLogPreparedStatementCreator(AiAuditLog auditLog) {
        return con -> {
            final var prepareStatement = con.prepareStatement(INSERT_AUDIT_LOG_SQL);
            prepareStatement.setTimestamp(1, Timestamp.from(auditLog.getTimestamp()));
            prepareStatement.setString(2, auditLog.getChatModel());
            prepareStatement.setString(3, auditLog.getTrackingId());
            prepareStatement.setInt(4, auditLog.getInputTokensCount());
            prepareStatement.setInt(5, auditLog.getOutputTokensCount());
            prepareStatement.setInt(6, auditLog.getTotalTokensCount());
            return prepareStatement;
        };
    }
}
