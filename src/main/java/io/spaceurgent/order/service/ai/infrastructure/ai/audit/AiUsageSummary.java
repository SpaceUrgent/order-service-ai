package io.spaceurgent.order.service.ai.infrastructure.ai.audit;

import lombok.Builder;

import java.util.List;
import java.util.function.Function;

public record AiUsageSummary(
   Integer requestTotal,
   Integer inputTokensTotal,
   Integer outputTokensTotal,
   Integer tokensTotal,
   List<ChatModelUsageSummary> byChatModel
) {

    @Builder
    public AiUsageSummary {
    }

    public static AiUsageSummary create(List<ChatModelUsageSummary> chatModelUsageSummaryList) {
        return AiUsageSummary.builder()
                .requestTotal(getPropertySum(chatModelUsageSummaryList, ChatModelUsageSummary::requestTotal))
                .inputTokensTotal(getPropertySum(chatModelUsageSummaryList, ChatModelUsageSummary::inputTokensTotal))
                .outputTokensTotal(getPropertySum(chatModelUsageSummaryList, ChatModelUsageSummary::outputTokensTotal))
                .tokensTotal(getPropertySum(chatModelUsageSummaryList, ChatModelUsageSummary::tokensTotal))
                .byChatModel(chatModelUsageSummaryList)
                .build();
    }

    private static Integer getPropertySum(List<ChatModelUsageSummary> chatModelUsageSummary,
                                                   Function<ChatModelUsageSummary, Integer> propertyExtractor) {
        return chatModelUsageSummary.stream().map(propertyExtractor).reduce(0, Integer::sum);
    }
}
