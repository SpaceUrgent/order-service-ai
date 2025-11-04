package io.spaceurgent.order.service.ai.api;

import io.spaceurgent.order.service.ai.infrastructure.ai.audit.AiAuditService;
import io.spaceurgent.order.service.ai.infrastructure.ai.audit.AiUsageSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiAuditService aiAuditService;

    @GetMapping(value = "/usage", produces = MediaType.APPLICATION_JSON_VALUE)
    public AiUsageSummary getAiUsageSummary() {
        return aiAuditService.getAiUsageSummary();
    }
}
