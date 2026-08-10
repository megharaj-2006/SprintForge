package org.SprintForge.modules.ai.prompt;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.ai.entity.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PromptCompiler {

    private final PromptRenderer promptRenderer;

    public CompiledPrompt compile(PromptTemplate template, Map<String, Object> variables) {
        String renderedSystem = promptRenderer.render(template.getSystemPrompt(), variables);
        String renderedUser = promptRenderer.render(template.getUserPrompt(), variables);

        return new CompiledPrompt(renderedSystem, renderedUser);
    }

    public record CompiledPrompt(String systemPrompt, String userPrompt) {}
}
