package org.SprintForge.modules.workflow.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeMetricsResponse {
    private long totalNodeExecutions;
    private long successfulNodeExecutions;
    private long failedNodeExecutions;
    private double averageNodeDurationMillis;
    private List<NodeStat> topUsedNodes;
    private List<NodeStat> slowestNodes;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NodeStat {
        private String nodeKey;
        private long count;
        private double avgDurationMillis;
        private long failures;
    }
}
