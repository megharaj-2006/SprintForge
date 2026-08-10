package org.SprintForge.modules.ai.provider;

public interface AIProviderClient {
    String getProviderKey();
    AIResponse execute(AIRequest request);
    boolean isHealthy();
}
