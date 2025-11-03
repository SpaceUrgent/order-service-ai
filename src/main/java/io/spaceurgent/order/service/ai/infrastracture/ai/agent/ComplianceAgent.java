package io.spaceurgent.order.service.ai.infrastracture.ai.agent;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        chatMemory = "agentChatMemory",
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel"
)
public interface ComplianceAgent {

    @UserMessage("Is this message {{it}} is read request?")
    boolean isReadOperation(String text);
}
