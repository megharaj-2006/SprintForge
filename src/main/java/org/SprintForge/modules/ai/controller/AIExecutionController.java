package org.SprintForge.modules.ai.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.orchestration.PromptOrchestrator;
import org.SprintForge.modules.ai.provider.AIRequest;
import org.SprintForge.modules.ai.provider.AIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIExecutionController {

    private final PromptOrchestrator promptOrchestrator;

    @Data
    public static class AIExecutionDTORequest {
        private String prompt;
        private String contentType;
        private String classificationType;
        private String entityType;
        private String targetLanguage;
        private String targetTone;
        private String policyKey = "BALANCED";
    }

    @PostMapping("/generate")
    public ResponseEntity<AIResponse> generate(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.generate")
                .prompt(request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/summarize")
    public ResponseEntity<AIResponse> summarize(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.summarize")
                .prompt(request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/classify")
    public ResponseEntity<AIResponse> classify(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.classify")
                .prompt(request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/extract")
    public ResponseEntity<AIResponse> extract(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.extract")
                .prompt(request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/translate")
    public ResponseEntity<AIResponse> translate(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.translate")
                .prompt("Translate to " + request.getTargetLanguage() + ": " + request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/rewrite")
    public ResponseEntity<AIResponse> rewrite(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.generate")
                .prompt("Rewrite with " + request.getTargetTone() + " tone: " + request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/predict")
    public ResponseEntity<AIResponse> predict(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.prediction")
                .prompt(request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }

    @PostMapping("/analyze")
    public ResponseEntity<AIResponse> analyze(@RequestBody AIExecutionDTORequest request) {
        AIRequest aiReq = AIRequest.builder()
                .capabilityKey("ai.reasoning")
                .prompt(request.getPrompt())
                .build();
        return ResponseEntity.ok(promptOrchestrator.orchestrateExecution(aiReq, request.getPolicyKey()));
    }
}
