package org.SprintForge.modules.workflow.wdl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class WdlProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String serializeToWdlJson(WorkflowDefinitionLanguage wdl) {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wdl);
    }

    @SneakyThrows
    public WorkflowDefinitionLanguage deserializeFromWdlJson(String jsonPayload) {
        return objectMapper.readValue(jsonPayload, WorkflowDefinitionLanguage.class);
    }
}
