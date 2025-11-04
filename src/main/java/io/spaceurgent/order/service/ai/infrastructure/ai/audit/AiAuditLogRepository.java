package io.spaceurgent.order.service.ai.infrastructure.ai.audit;

import java.util.List;

public interface AiAuditLogRepository {

    void save(AiAuditLog auditLog);

    List<ChatModelUsageSummary> getChatModelUsageSummary();
}
