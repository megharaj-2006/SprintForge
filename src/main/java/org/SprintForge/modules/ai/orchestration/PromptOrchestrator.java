package org.SprintForge.modules.ai.orchestration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.ai.capability.CapabilityResolver;
import org.SprintForge.modules.ai.entity.AIExecution;
import org.SprintForge.modules.ai.policy.AIExecutionPolicy;
import org.SprintForge.modules.ai.policy.PolicyResolver;
import org.SprintForge.modules.ai.provider.AIProviderClient;
import org.SprintForge.modules.ai.provider.AIRequest;
import org.SprintForge.modules.ai.provider.AIResponse;
import org.SprintForge.modules.ai.repository.AIExecutionRepository;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class PromptOrchestrator {

    private final CapabilityResolver capabilityResolver;
    private final PolicyResolver policyResolver;
    private final OutputFormatter outputFormatter;
    private final AIExecutionRepository executionRepository;

    public AIResponse orchestrateExecution(AIRequest request, String requestedPolicyKey) {
        long startTime = System.currentTimeMillis();
        AIExecutionPolicy policy = policyResolver.resolvePolicy(requestedPolicyKey);

        String providerKey = (request.getProviderKey() != null && !request.getProviderKey().isBlank())
                ? request.getProviderKey()
                : policy.getPreferredProvider();

        AIProviderClient client = capabilityResolver.resolveProviderForCapability(request.getCapabilityKey(), providerKey);

        request.setProviderKey(client.getProviderKey());
        if (request.getModelKey() == null && policy.getPreferredModel() != null) {
            request.setModelKey(policy.getPreferredModel());
        }

        log.info("PromptOrchestrator dispatching request to provider: {} using policy: {}", client.getProviderKey(), policy.getPolicyKey());

        AIResponse response = client.execute(request);
        long duration = System.currentTimeMillis() - startTime;
        response.setLatencyMs(duration);

        if (response.getContent() != null) {
            response.setContent(outputFormatter.formatMarkdown(response.getContent()));
        }

        // Record execution in DB audit log
        try {
            AIExecution audit = new AIExecution();
            audit.setCapabilityKey(request.getCapabilityKey() != null ? request.getCapabilityKey() : "ai.generate");
            audit.setProviderKey(response.getProviderKey());
            audit.setModelKey(response.getModelKey());
            audit.setStatus("COMPLETED");
            audit.setStartedAt(LocalDateTime.now().minusNanos(duration * 1_000_000));
            audit.setCompletedAt(LocalDateTime.now());
            audit.setDurationMs(duration);
            if (response.getUsage() != null) {
                audit.setInputTokens(response.getUsage().getPromptTokens());
                audit.setOutputTokens(response.getUsage().getCompletionTokens());
            }
            audit.setEstimatedCost(response.getEstimatedCost());
            executionRepository.save(audit);
        } catch (Exception e) {
            log.warn("Failed to audit AIExecution: {}", e.getMessage());
        }

        return response;
    }
}
