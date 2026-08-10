package org.SprintForge.modules.ai.prompt;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PromptRenderer {

    public String render(String templateText, Map<String, Object> variables) {
        if (templateText == null) return "";
        if (variables == null || variables.isEmpty()) return templateText;

        String result = templateText;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String placeholder1 = "${" + entry.getKey() + "}";
            String placeholder2 = "{{" + entry.getKey() + "}}";
            String valStr = entry.getValue() != null ? entry.getValue().toString() : "";
            result = result.replace(placeholder1, valStr).replace(placeholder2, valStr);
        }
        return result;
    }
}
