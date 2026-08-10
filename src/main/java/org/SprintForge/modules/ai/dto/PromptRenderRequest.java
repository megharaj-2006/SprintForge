package org.SprintForge.modules.ai.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PromptRenderRequest {
    private String templateKey;
    private Map<String, Object> variables;
}
