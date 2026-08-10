package org.SprintForge.modules.workflow;

import org.SprintForge.modules.workflow.wdl.WdlProcessor;
import org.SprintForge.modules.workflow.wdl.WorkflowDefinitionLanguage;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WdlTest {

    @Test
    void testWdlSerializationAndDeserialization() {
        WdlProcessor processor = new WdlProcessor();

        WorkflowDefinitionLanguage wdl = WorkflowDefinitionLanguage.builder()
                .wdlVersion("1.0.0")
                .metadata(WorkflowDefinitionLanguage.Metadata.builder()
                        .name("Sample WDL Workflow")
                        .description("Testing canonical schema")
                        .category("TEST")
                        .build())
                .nodes(List.of(
                        WorkflowDefinitionLanguage.WdlNode.builder()
                                .id("1")
                                .nodeKey("manual.trigger")
                                .name("Start")
                                .nodeType("TRIGGER")
                                .x(100.0)
                                .y(100.0)
                                .configuration(Map.of("sampleConfig", "value"))
                                .build()
                ))
                .connections(List.of())
                .variables(List.of())
                .build();

        String json = processor.serializeToWdlJson(wdl);
        assertNotNull(json);

        WorkflowDefinitionLanguage parsed = processor.deserializeFromWdlJson(json);
        assertEquals("Sample WDL Workflow", parsed.getMetadata().getName());
        assertEquals(1, parsed.getNodes().size());
        assertEquals("manual.trigger", parsed.getNodes().get(0).getNodeKey());
    }
}
