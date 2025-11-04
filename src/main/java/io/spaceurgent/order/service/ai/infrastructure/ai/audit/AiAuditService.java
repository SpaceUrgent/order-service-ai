package io.spaceurgent.order.service.ai.infrastructure.ai.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiAuditService {
    private final AiAuditLogRepository auditLogRepository;

    public void save(AiAuditLog auditLog) {
        assert auditLog != null : "Audit log is required";
        auditLogRepository.save(auditLog);
    }

    public AiUsageSummary getAiUsageSummary() {
        return AiUsageSummary.create(auditLogRepository.getChatModelUsageSummary());
    }
}
