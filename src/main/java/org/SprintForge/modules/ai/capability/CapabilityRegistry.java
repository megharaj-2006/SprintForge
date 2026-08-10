package org.SprintForge.modules.ai.capability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.ai.entity.AICapability;
import org.SprintForge.modules.ai.repository.AICapabilityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CapabilityRegistry {

    private final AICapabilityRepository capabilityRepository;

    public Optional<AICapability> getCapability(String capabilityKey) {
        return capabilityRepository.findByCapabilityKey(capabilityKey);
    }

    public List<AICapability> getAllCapabilities() {
        return capabilityRepository.findAll();
    }
}
