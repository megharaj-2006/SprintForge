package org.SprintForge.modules.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptResponse {
    private String templateKey;
    private String name;
    private String compiledSystemPrompt;
    private String compiledUserPrompt;
}
