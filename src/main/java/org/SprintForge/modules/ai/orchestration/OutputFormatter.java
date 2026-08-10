package org.SprintForge.modules.ai.orchestration;

import org.springframework.stereotype.Component;

@Component
public class OutputFormatter {

    public String formatMarkdown(String rawText) {
        if (rawText == null) return "";
        return rawText.trim();
    }
}
