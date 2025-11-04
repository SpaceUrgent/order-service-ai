package io.spaceurgent.order.service.ai.infrastructure.ai;

import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import io.spaceurgent.order.service.ai.infrastructure.ai.audit.AiAuditLogMapper;
import io.spaceurgent.order.service.ai.infrastructure.ai.audit.AiAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatModelListenerImpl implements ChatModelListener {
    private final AiAuditService aiAuditService;
    private final AiAuditLogMapper auditLogMapper;

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        final var auditLog = auditLogMapper.toAuditLog(responseContext);
        aiAuditService.save(auditLog);
        ChatModelListener.super.onResponse(responseContext);
    }
}
