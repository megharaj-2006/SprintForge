package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.task.dto.response.DependencyGraphResponse;
import org.SprintForge.modules.workspace.task.entity.AdvancedTaskRelationship;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.AdvancedTaskRelationshipRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DependencyGraphService {

    private final AdvancedTaskRelationshipRepository relationshipRepository;
    private final TaskRepository taskRepository;

    public void checkForCircularDependency(Long sourceTaskId, Long targetTaskId) {
        if (sourceTaskId.equals(targetTaskId)) {
            throw new BusinessRuleException("A task cannot depend on itself");
        }

        Set<Long> visited = new HashSet<>();
        if (dfsCheckCycle(targetTaskId, sourceTaskId, visited)) {
            throw new BusinessRuleException("Circular dependency detected between task " + sourceTaskId + " and task " + targetTaskId);
        }
    }

    private boolean dfsCheckCycle(Long currentId, Long targetId, Set<Long> visited) {
        if (currentId.equals(targetId)) {
            return true;
        }
        if (!visited.add(currentId)) {
            return false;
        }

        List<AdvancedTaskRelationship> outgoing = relationshipRepository.findBySourceTaskIdAndRelationshipTypeAndIsDeletedFalse(currentId, "BLOCKS");
        for (AdvancedTaskRelationship rel : outgoing) {
            if (dfsCheckCycle(rel.getTargetTaskId(), targetId, visited)) {
                return true;
            }
        }

        return false;
    }

    @Transactional(readOnly = true)
    public DependencyGraphResponse buildGraph(Long rootTaskId) {
        List<DependencyGraphResponse.GraphNode> nodes = new ArrayList<>();
        List<DependencyGraphResponse.GraphEdge> edges = new ArrayList<>();
        Set<Long> visitedTasks = new HashSet<>();

        Queue<Long> queue = new LinkedList<>();
        queue.add(rootTaskId);
        visitedTasks.add(rootTaskId);

        while (!queue.isEmpty()) {
            Long currentId = queue.poll();
            Task task = taskRepository.findById(currentId).orElse(null);
            if (task != null) {
                nodes.add(DependencyGraphResponse.GraphNode.builder()
                        .taskId(task.getId())
                        .title(task.getTitle())
                        .status(task.getStatus().name())
                        .build());
            }

            List<AdvancedTaskRelationship> outgoing = relationshipRepository.findBySourceTaskIdAndIsDeletedFalse(currentId);
            for (AdvancedTaskRelationship rel : outgoing) {
                edges.add(DependencyGraphResponse.GraphEdge.builder()
                        .sourceTaskId(rel.getSourceTaskId())
                        .targetTaskId(rel.getTargetTaskId())
                        .relationshipType(rel.getRelationshipType())
                        .build());

                if (visitedTasks.add(rel.getTargetTaskId())) {
                    queue.add(rel.getTargetTaskId());
                }
            }
        }

        return DependencyGraphResponse.builder()
                .rootTaskId(rootTaskId)
                .nodes(nodes)
                .edges(edges)
                .build();
    }
}
