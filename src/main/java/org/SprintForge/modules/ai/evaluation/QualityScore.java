package org.SprintForge.modules.ai.evaluation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityScore {
    private double overallScore; // 0.0 to 1.0
    private double coherenceScore;
    private double relevanceScore;
    private double safetyScore;
    private boolean hallucinationDetected;
}
