package io.spaceurgent.order.service.ai.infrastructure.ai.agent;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService(
        chatMemory = "agentChatMemory"
)
public interface ComplianceAgent {

    @UserMessage("""
            Determine whether the following user message is a *read-only data query*
            that simply asks for information or analysis — for example:
            "Show total sales", "List top customers", "What is the average order size".
            Return `true` only if the message clearly requests to *read* or *analyze* existing data
            without making any change to data or system state.
            Return `false` if the message suggests *writing, updating, deleting, inserting*,
            *modifying data*, or performing any *administrative, configuration, or system* action.
            User message: {{it}}
            """)
    boolean isReadOperation(String text);
}
