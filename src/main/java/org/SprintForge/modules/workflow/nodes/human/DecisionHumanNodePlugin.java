package org.SprintForge.modules.workflow.nodes.human;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DecisionHumanNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("decision.human")
                .displayName("Human Decision")
                .category("HUMAN")
                .icon("git-pull-request")
                .description("Presents a custom choice selection to a human decision maker.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("selectedOption").type("STRING").description("Selected option key").build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("APPROVE").description("Approved choice").build(),
                        NodeBranchPort.label("REJECT").description("Rejected choice").build(),
                        NodeBranchPort.label("CUSTOM").description("Custom option choice").build()
                ))
                .capabilities(NodeCapabilities.builder().humanInteraction(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String decision = request.getConfiguration().getString("selectedOption", "APPROVE");
        return NodeExecutionResult.success(Map.of("selectedOption", decision), decision);
    }
}
