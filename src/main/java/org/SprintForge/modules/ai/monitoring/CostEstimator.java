package org.SprintForge.modules.ai.monitoring;

import org.springframework.stereotype.Component;

@Component
public class CostEstimator {

    public double estimateCost(String modelKey, int inputTokens, int outputTokens) {
        if (modelKey == null) return 0.0;

        String key = modelKey.toLowerCase();
        double inputRatePer1k = 0.0015;
        double outputRatePer1k = 0.0020;

        if (key.contains("gpt-4o-mini")) {
            inputRatePer1k = 0.00015;
            outputRatePer1k = 0.00060;
        } else if (key.contains("gpt-4o")) {
            inputRatePer1k = 0.0050;
            outputRatePer1k = 0.0150;
        } else if (key.contains("claude-3-5-sonnet")) {
            inputRatePer1k = 0.0030;
            outputRatePer1k = 0.0150;
        } else if (key.contains("gemini-1.5-pro")) {
            inputRatePer1k = 0.0035;
            outputRatePer1k = 0.0105;
        } else if (key.contains("llama") || key.contains("ollama")) {
            return 0.0; // Local execution cost
        }

        return ((inputTokens / 1000.0) * inputRatePer1k) + ((outputTokens / 1000.0) * outputRatePer1k);
    }
}
