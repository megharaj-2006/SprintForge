package org.SprintForge.modules.ai.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PolicyResolver {

    private final AIExecutionPolicyRepository policyRepository;

    public AIExecutionPolicy resolvePolicy(String requestedPolicyKey) {
        if (requestedPolicyKey != null && !requestedPolicyKey.isBlank()) {
            Optional<AIExecutionPolicy> pol = policyRepository.findByPolicyKey(requestedPolicyKey.toUpperCase());
            if (pol.isPresent()) {
                log.info("Resolved execution policy {}", pol.get().getPolicyKey());
                return pol.get();
            }
        }

        // Default BALANCED policy fallback
        return policyRepository.findByPolicyKey("BALANCED")
                .orElseGet(() -> {
                    AIExecutionPolicy fallback = new AIExecutionPolicy();
                    fallback.setPolicyKey("BALANCED");
                    fallback.setPreferredProvider("openai");
                    fallback.setPreferredModel("gpt-4o");
                    return fallback;
                });
    }
}
