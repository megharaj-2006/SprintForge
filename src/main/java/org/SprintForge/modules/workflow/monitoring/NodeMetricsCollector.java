package org.SprintForge.modules.workflow.monitoring;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.entity.NodeExecution;
import org.SprintForge.modules.workflow.entity.enums.NodeExecutionStatus;
import org.SprintForge.modules.workflow.repository.NodeExecutionRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NodeMetricsCollector {

    private final NodeExecutionRepository nodeExecutionRepository;

    public NodeMetricsResponse collectMetrics() {
        List<NodeExecution> all = nodeExecutionRepository.findAll();
        long total = all.size();
        long success = all.stream().filter(e -> e.getStatus() == NodeExecutionStatus.COMPLETED).count();
        long failed = all.stream().filter(e -> e.getStatus() == NodeExecutionStatus.FAILED).count();

        double avgDuration = all.stream()
                .filter(e -> e.getDuration() != null)
                .mapToLong(NodeExecution::getDuration)
                .average()
                .orElse(0.0);

        Map<String, List<NodeExecution>> grouped = all.stream()
                .filter(e -> e.getWorkflowNode() != null && e.getWorkflowNode().getDefinition() != null)
                .collect(Collectors.groupingBy(e -> e.getWorkflowNode().getDefinition().getNodeKey()));

        List<NodeMetricsResponse.NodeStat> stats = new ArrayList<>();
        grouped.forEach((key, list) -> {
            long c = list.size();
            long f = list.stream().filter(e -> e.getStatus() == NodeExecutionStatus.FAILED).count();
            double avg = list.stream().filter(e -> e.getDuration() != null).mapToLong(NodeExecution::getDuration).average().orElse(0.0);
            stats.add(new NodeMetricsResponse.NodeStat(key, c, avg, f));
        });

        List<NodeMetricsResponse.NodeStat> topUsed = stats.stream()
                .sorted(Comparator.comparingLong(NodeMetricsResponse.NodeStat::getCount).reversed())
                .limit(5)
                .collect(Collectors.toList());

        List<NodeMetricsResponse.NodeStat> slowest = stats.stream()
                .sorted(Comparator.comparingDouble(NodeMetricsResponse.NodeStat::getAvgDurationMillis).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return NodeMetricsResponse.builder()
                .totalNodeExecutions(total)
                .successfulNodeExecutions(success)
                .failedNodeExecutions(failed)
                .averageNodeDurationMillis(avgDuration)
                .topUsedNodes(topUsed)
                .slowestNodes(slowest)
                .build();
    }
}
