package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ContainsConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("contains.condition")
                .displayName("Contains Condition")
                .category("CONDITION")
                .icon("search")
                .description("Checks if target string or collection contains the specified substring or item.")
                .inputs(List.of(
                        NodeInputPort.builder().name("source").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("substring").type("STRING").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Contains match").build(),
                        NodeBranchPort.label("FALSE").description("No match").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String source = request.getConfiguration().getString("source", "");
        String substring = request.getConfiguration().getString("substring", "");

        boolean contains = source.contains(substring);
        String branch = contains ? "TRUE" : "FALSE";

        return NodeExecutionResult.success(Map.of("contains", contains), branch);
    }
}
