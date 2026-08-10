package org.SprintForge.modules.ai.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.ai.dto.PromptRenderRequest;
import org.SprintForge.modules.ai.dto.PromptResponse;
import org.SprintForge.modules.ai.entity.AICapability;
import org.SprintForge.modules.ai.entity.AIModel;
import org.SprintForge.modules.ai.entity.AIProvider;
import org.SprintForge.modules.ai.entity.PromptTemplate;
import org.SprintForge.modules.ai.prompt.PromptCompiler;
import org.SprintForge.modules.ai.repository.AICapabilityRepository;
import org.SprintForge.modules.ai.repository.AIModelRepository;
import org.SprintForge.modules.ai.repository.AIProviderRepository;
import org.SprintForge.modules.ai.repository.PromptTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIPlatformServiceImpl implements AIPlatformService {

    private final AIProviderRepository providerRepository;
    private final AIModelRepository modelRepository;
    private final AICapabilityRepository capabilityRepository;
    private final PromptTemplateRepository promptTemplateRepository;
    private final PromptCompiler promptCompiler;

    @Override
    @Transactional(readOnly = true)
    public List<AIProvider> getProviders() {
        return providerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AIModel> getModels() {
        return modelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AICapability> getCapabilities() {
        return capabilityRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromptTemplate> getPrompts() {
        return promptTemplateRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PromptResponse renderPrompt(PromptRenderRequest request) {
        PromptTemplate template = promptTemplateRepository.findByTemplateKey(request.getTemplateKey())
                .orElseThrow(() -> new ResourceNotFoundException("Prompt template not found for key: " + request.getTemplateKey()));

        PromptCompiler.CompiledPrompt compiled = promptCompiler.compile(template, request.getVariables());

        return PromptResponse.builder()
                .templateKey(template.getTemplateKey())
                .name(template.getName())
                .compiledSystemPrompt(compiled.systemPrompt())
                .compiledUserPrompt(compiled.userPrompt())
                .build();
    }
}
