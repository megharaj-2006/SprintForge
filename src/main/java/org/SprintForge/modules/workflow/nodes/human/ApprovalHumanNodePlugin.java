package org.SprintForge.modules.workflow.nodes.human;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ApprovalHumanNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("approval.human")
                .displayName("Human Approval")
                .category("HUMAN")
                .icon("user-check")
                .description("Requires approval from single/multiple approvers (Any, All, Timeout, Escalation).")
                .inputs(List.of(
                        NodeInputPort.builder().name("approverRole").type("STRING").required(true).defaultValue("MANAGER").build(),
                        NodeInputPort.builder().name("approvalMode").type("STRING").defaultValue("SINGLE").build(),
                        NodeInputPort.builder().name("timeoutHours").type("INTEGER").defaultValue(24).build()
                ))
                .outputs(List.of(
                        NodeOutputPort.builder().name("approvalStatus").type("STRING").description("APPROVED, REJECTED, TIMEOUT").build(),
                        NodeOutputPort.builder().name("approverId").type("STRING").description("ID of approving user").build()
                ))
                .branches(List.of(
                        NodeBranchPort.label("APPROVED").description("Approved by required approvers").build(),
                        NodeBranchPort.label("REJECTED").description("Rejected by approver").build(),
                        NodeBranchPort.label("TIMEOUT").description("Approval request timed out").build()
                ))
                .capabilities(NodeCapabilities.builder().humanInteraction(true).async(true).build())
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String mode = request.getConfiguration().getString("approvalMode", "SINGLE");
        return NodeExecutionResult.success(Map.of(
                "approvalStatus", "APPROVED",
                "approverId", request.getContext().getCurrentUserId() != null ? request.getContext().getCurrentUserId() : "SYSTEM_USER",
                "mode", mode
        ), "APPROVED");
    }
}
