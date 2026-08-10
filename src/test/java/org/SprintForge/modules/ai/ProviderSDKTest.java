package org.SprintForge.modules.ai;

import org.SprintForge.modules.ai.provider.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProviderSDKTest {

    @Test
    void testMockOpenAIClientExecution() {
        AIProviderClient client = new MockOpenAIClient();
        AIRequest request = AIRequest.builder()
                .capabilityKey("ai.generate")
                .prompt("Explain quantum computing")
                .providerKey("openai")
                .modelKey("gpt-4o")
                .build();

        AIResponse response = client.execute(request);

        assertNotNull(response);
        assertEquals("openai", response.getProviderKey());
        assertEquals("gpt-4o", response.getModelKey());
        assertTrue(response.getContent().contains("[Mock OpenAI Response]"));
        assertEquals(105, response.getUsage().getTotalTokens());
        assertTrue(response.getEstimatedCost() > 0);
    }

    @Test
    void testAIProviderRegistry() {
        AIProviderClient openAi = new MockOpenAIClient();
        AIProviderClient gemini = new MockGeminiClient();
        AIProviderRegistry registry = new AIProviderRegistry(List.of(openAi, gemini));

        assertEquals(openAi, registry.getClient("openai"));
        assertEquals(gemini, registry.getClient("gemini"));
        assertTrue(registry.getClient("openai").isHealthy());
    }
}
