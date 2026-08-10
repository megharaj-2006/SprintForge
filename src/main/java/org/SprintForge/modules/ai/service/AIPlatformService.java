package org.SprintForge.modules.ai.service;

import org.SprintForge.modules.ai.dto.PromptRenderRequest;
import org.SprintForge.modules.ai.dto.PromptResponse;
import org.SprintForge.modules.ai.entity.AICapability;
import org.SprintForge.modules.ai.entity.AIModel;
import org.SprintForge.modules.ai.entity.AIProvider;
import org.SprintForge.modules.ai.entity.PromptTemplate;

import java.util.List;

public interface AIPlatformService {
    List<AIProvider> getProviders();
    List<AIModel> getModels();
    List<AICapability> getCapabilities();
    List<PromptTemplate> getPrompts();
    PromptResponse renderPrompt(PromptRenderRequest request);
}
