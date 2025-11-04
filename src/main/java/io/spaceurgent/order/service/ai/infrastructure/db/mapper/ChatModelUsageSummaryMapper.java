package io.spaceurgent.order.service.ai.infrastructure.db.mapper;

import io.spaceurgent.order.service.ai.infrastructure.ai.audit.ChatModelUsageSummary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ChatModelUsageSummaryMapper implements RowMapper<ChatModelUsageSummary> {

    @Override
    public ChatModelUsageSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ChatModelUsageSummary.builder()
                .modelName(rs.getString("chat_model"))
                .requestTotal(rs.getInt("request_total"))
                .inputTokensTotal(rs.getInt("input_tokens_total"))
                .outputTokensTotal(rs.getInt("output_tokens_total"))
                .tokensTotal(rs.getInt("tokens_total"))
                .build();
    }
}
