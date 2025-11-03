package io.spaceurgent.order.service.ai.infrastracture.ai.service;

import dev.langchain4j.service.Result;
import io.spaceurgent.order.service.ai.application.dto.MessageDto;
import io.spaceurgent.order.service.ai.application.service.OrderAssistantService;
import io.spaceurgent.order.service.ai.infrastracture.ai.agent.ComplianceAgent;
import io.spaceurgent.order.service.ai.infrastracture.ai.agent.SqlAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiOrderAssistantService implements OrderAssistantService {
    private final ComplianceAgent complianceAgent;
    private final SqlAgent sqlAgent;

    @Override
    public MessageDto ask(MessageDto userMessage) {
        assert userMessage != null : "User message is required";
        final var userQuestion = userMessage.content();
        log.debug("User question: {}", userQuestion);
        if (!complianceAgent.isReadOperation(userQuestion)) {
            log.warn("Question contains illegal instructions");
            return new MessageDto("Only read request are supported");
        }
        Result<String> result = sqlAgent.provideInformation(userQuestion);
        log.debug("Result: {}", result);
        return new MessageDto(result.content());
    }
}
