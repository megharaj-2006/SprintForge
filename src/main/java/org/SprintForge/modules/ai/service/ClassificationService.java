package org.SprintForge.modules.ai.service;

public interface ClassificationService {
    String classify(String classificationType, String textContent, String policyKey);
}
