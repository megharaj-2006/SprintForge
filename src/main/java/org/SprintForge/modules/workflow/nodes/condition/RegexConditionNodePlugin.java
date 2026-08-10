package org.SprintForge.modules.workflow.nodes.condition;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class RegexConditionNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("regex.condition")
                .displayName("Regex Condition")
                .category("CONDITION")
                .icon("code")
                .description("Tests a target string against a regular expression pattern.")
                .inputs(List.of(
                        NodeInputPort.builder().name("text").type("STRING").required(true).build(),
                        NodeInputPort.builder().name("pattern").type("STRING").required(true).build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("TRUE").description("Pattern matched").build(),
                        NodeBranchPort.label("FALSE").description("Pattern did not match").build()
                ))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String text = request.getConfiguration().getString("text", "");
        String patternStr = request.getConfiguration().getString("pattern", ".*");

        boolean matches = Pattern.compile(patternStr).matcher(text).find();
        String branch = matches ? "TRUE" : "FALSE";

        return NodeExecutionResult.success(Map.of("matches", matches), branch);
    }
}
