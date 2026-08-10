package org.SprintForge.modules.workflow.nodes.trigger;

import org.SprintForge.modules.workflow.sdk.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ScheduledTriggerNodePlugin extends AbstractNodePlugin {

    @Override
    public NodeDescriptor getDescriptor() {
        return NodeDescriptor.builder()
                .nodeKey("schedule.trigger")
                .displayName("Scheduled Trigger")
                .category("TRIGGER")
                .icon("clock")
                .description("Triggers workflow on a recurring Cron or interval schedule.")
                .outputs(List.of(
                        NodeOutputPort.builder().name("scheduledTime").type("LONG").description("Scheduled execution timestamp").build(),
                        NodeOutputPort.builder().name("cronExpression").type("STRING").description("Active cron schedule").build()
                ))
                .branches(List.of(NodeBranchPort.builder().label("SUCCESS").description("On schedule trigger event").build()))
                .build();
    }

    @Override
    public NodeExecutionResult executePlugin(NodeExecutionRequest request) {
        String cron = request.getConfiguration().getString("cronExpression", "0 * * * *");
        return NodeExecutionResult.success(Map.of(
                "scheduledTime", System.currentTimeMillis(),
                "cronExpression", cron
        ));
    }
}
