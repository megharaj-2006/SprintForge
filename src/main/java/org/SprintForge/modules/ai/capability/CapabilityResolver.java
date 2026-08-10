package org.SprintForge.modules.ai.capability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.ai.entity.AICapability;
import org.SprintForge.modules.ai.provider.AIProviderClient;
import org.SprintForge.modules.ai.provider.AIProviderRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CapabilityResolver {

    private final CapabilityRegistry capabilityRegistry;
    private final AIProviderRegistry providerRegistry;

    public AIProviderClient resolveProviderForCapability(String capabilityKey, String requestedProviderKey) {
        if (requestedProviderKey != null && !requestedProviderKey.isBlank()) {
            return providerRegistry.getClient(requestedProviderKey);
        }

        Optional<AICapability> cap = capabilityRegistry.getCapability(capabilityKey);
        if (cap.isPresent() && cap.get().getDefaultProviderKey() != null) {
            String defaultKey = cap.get().getDefaultProviderKey();
            log.info("Resolved capability {} to default provider {}", capabilityKey, defaultKey);
            return providerRegistry.getClient(defaultKey);
        }

        log.info("Fallback capability {} to default OpenAI provider", capabilityKey);
        return providerRegistry.getClient("openai");
    }
}
