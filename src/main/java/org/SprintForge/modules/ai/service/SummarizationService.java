package org.SprintForge.modules.ai.service;

public interface SummarizationService {
    String summarize(String contentType, String textContent, String policyKey);
}
