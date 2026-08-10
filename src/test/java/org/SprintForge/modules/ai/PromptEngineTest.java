package org.SprintForge.modules.ai;

import org.SprintForge.modules.ai.entity.PromptTemplate;
import org.SprintForge.modules.ai.prompt.PromptCompiler;
import org.SprintForge.modules.ai.prompt.PromptRenderer;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PromptEngineTest {

    @Test
    void testPromptRendererVariableSubstitution() {
        PromptRenderer renderer = new PromptRenderer();
        String template = "Hello ${name}, welcome to {{platform}}!";
        String rendered = renderer.render(template, Map.of("name", "Alice", "platform", "SprintForge"));

        assertEquals("Hello Alice, welcome to SprintForge!", rendered);
    }

    @Test
    void testPromptCompiler() {
        PromptRenderer renderer = new PromptRenderer();
        PromptCompiler compiler = new PromptCompiler(renderer);

        PromptTemplate template = new PromptTemplate();
        template.setSystemPrompt("You are an assistant for ${user}");
        template.setUserPrompt("Summarize ${topic}");

        PromptCompiler.CompiledPrompt compiled = compiler.compile(template, Map.of("user", "Bob", "topic", "AI Architecture"));

        assertEquals("You are an assistant for Bob", compiled.systemPrompt());
        assertEquals("Summarize AI Architecture", compiled.userPrompt());
    }
}
