package io.spaceurgent.order.service.ai.infrastructure.ai;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatMemory agentChatMemory() {
        return new MessageWindowChatMemory.Builder().maxMessages(50).build();
    }
}
