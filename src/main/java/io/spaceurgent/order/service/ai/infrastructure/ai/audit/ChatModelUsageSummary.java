package io.spaceurgent.order.service.ai.infrastructure.ai.audit;

import lombok.Builder;

public record ChatModelUsageSummary(
        String modelName,
        Integer requestTotal,
        Integer inputTokensTotal,
        Integer outputTokensTotal,
        Integer tokensTotal
) {

    @Builder
    public ChatModelUsageSummary {
    }
}
