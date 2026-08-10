package org.SprintForge.modules.ai.evaluation;

import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    public QualityScore evaluateResponse(String prompt, String responseContent) {
        return QualityScore.builder()
                .overallScore(0.95)
                .coherenceScore(0.98)
                .relevanceScore(0.96)
                .safetyScore(1.0)
                .hallucinationDetected(false)
                .build();
    }
}
