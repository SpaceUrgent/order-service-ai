package io.spaceurgent.order.service.ai.application.service;

import io.spaceurgent.order.service.ai.application.dto.MessageDto;

public interface OrderAssistantService {

    MessageDto ask(MessageDto userMessage);
}
