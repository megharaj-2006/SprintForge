package org.SprintForge.modules.ai.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.dto.AIStatisticsResponse;
import org.SprintForge.modules.ai.dto.PromptRenderRequest;
import org.SprintForge.modules.ai.dto.PromptResponse;
import org.SprintForge.modules.ai.entity.AICapability;
import org.SprintForge.modules.ai.entity.AIModel;
import org.SprintForge.modules.ai.entity.AIProvider;
import org.SprintForge.modules.ai.entity.PromptTemplate;
import org.SprintForge.modules.ai.monitoring.AIMonitoringService;
import org.SprintForge.modules.ai.provider.AIProviderHealthChecker;
import org.SprintForge.modules.ai.service.AIPlatformService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIPlatformController {

    private final AIPlatformService platformService;
    private final AIProviderHealthChecker healthChecker;
    private final AIMonitoringService monitoringService;

    @GetMapping("/providers")
    public ResponseEntity<List<AIProvider>> getProviders() {
        return ResponseEntity.ok(platformService.getProviders());
    }

    @GetMapping("/models")
    public ResponseEntity<List<AIModel>> getModels() {
        return ResponseEntity.ok(platformService.getModels());
    }

    @GetMapping("/capabilities")
    public ResponseEntity<List<AICapability>> getCapabilities() {
        return ResponseEntity.ok(platformService.getCapabilities());
    }

    @GetMapping("/prompts")
    public ResponseEntity<List<PromptTemplate>> getPrompts() {
        return ResponseEntity.ok(platformService.getPrompts());
    }

    @PostMapping("/prompts/render")
    public ResponseEntity<PromptResponse> renderPrompt(@RequestBody PromptRenderRequest request) {
        return ResponseEntity.ok(platformService.renderPrompt(request));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Boolean>> getHealth() {
        return ResponseEntity.ok(healthChecker.checkAllHealth());
    }

    @GetMapping("/statistics")
    public ResponseEntity<AIStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(monitoringService.getStatistics());
    }
}
