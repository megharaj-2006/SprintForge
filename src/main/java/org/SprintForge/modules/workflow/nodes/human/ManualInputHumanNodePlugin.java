package org.SprintForge.modules.workflow.nodes.human;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ManualInputHumanNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("manualInput.human")
                .displayName("Manual Input")
                .category("HUMAN")
                .icon("edit-3")
                .description("Pauses workflow to request form input values from a user.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("submittedValues").type("OBJECT").description("User-submitted form fields").build()
                ))
                .branches(List.of(NodeBranchPort.label("SUCCESS").description("Form submitted").build()))
                .capabilities(NodeCapabilities.builder().humanInteraction(true).async(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        return NodeExecutionResult.success(Map.of("submittedValues", request.getInputData() != null ? request.getInputData() : Map.of()));
    }
}
