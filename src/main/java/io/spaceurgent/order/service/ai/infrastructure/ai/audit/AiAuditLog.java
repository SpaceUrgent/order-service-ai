package io.spaceurgent.order.service.ai.infrastructure.ai.audit;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class AiAuditLog {
    private UUID id;
    private Instant timestamp;
    private String chatModel;
    private String trackingId;
    private Integer inputTokensCount;
    private Integer outputTokensCount;
    private Integer totalTokensCount;
}
