package io.spaceurgent.order.service.ai.infrastructure.ai.audit;

import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AiAuditLogMapper {

    public AiAuditLog toAuditLog(ChatModelResponseContext responseContext) {
        ChatResponse chatResponse = responseContext.chatResponse();
        TokenUsage tokenUsage = chatResponse.tokenUsage();
        return AiAuditLog.builder()
                .timestamp(Instant.now())
                .chatModel(chatResponse.metadata().modelName())
                .trackingId(chatResponse.metadata().id())
                .inputTokensCount(tokenUsage.inputTokenCount())
                .outputTokensCount(tokenUsage.outputTokenCount())
                .totalTokensCount(tokenUsage.totalTokenCount())
                .build();
    }
}
