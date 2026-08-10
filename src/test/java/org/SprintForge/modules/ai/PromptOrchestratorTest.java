package org.SprintForge.modules.ai;

import org.SprintForge.modules.ai.capability.CapabilityResolver;
import org.SprintForge.modules.ai.orchestration.OutputFormatter;
import org.SprintForge.modules.ai.orchestration.PromptOrchestrator;
import org.SprintForge.modules.ai.policy.AIExecutionPolicy;
import org.SprintForge.modules.ai.policy.PolicyResolver;
import org.SprintForge.modules.ai.provider.*;
import org.SprintForge.modules.ai.repository.AIExecutionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PromptOrchestratorTest {

    @Test
    void testOrchestratorExecution() {
        CapabilityResolver capabilityResolver = Mockito.mock(CapabilityResolver.class);
        PolicyResolver policyResolver = Mockito.mock(PolicyResolver.class);
        AIExecutionRepository repository = Mockito.mock(AIExecutionRepository.class);
        OutputFormatter formatter = new OutputFormatter();

        AIProviderClient mockClient = new MockOpenAIClient();
        when(capabilityResolver.resolveProviderForCapability(anyString(), any())).thenReturn(mockClient);

        AIExecutionPolicy policy = new AIExecutionPolicy();
        policy.setPolicyKey("BALANCED");
        policy.setPreferredProvider("openai");
        policy.setPreferredModel("gpt-4o");
        when(policyResolver.resolvePolicy(any())).thenReturn(policy);

        PromptOrchestrator orchestrator = new PromptOrchestrator(capabilityResolver, policyResolver, formatter, repository);

        AIRequest request = AIRequest.builder()
                .capabilityKey("ai.generate")
                .prompt("Hello test")
                .build();

        AIResponse response = orchestrator.orchestrateExecution(request, "BALANCED");

        assertNotNull(response);
        assertEquals("openai", response.getProviderKey());
        assertTrue(response.getContent().contains("[Mock OpenAI Response]"));
    }
}
