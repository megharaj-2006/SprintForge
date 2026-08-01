package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskHealthResponse;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComplianceService {

    private final TaskRepository taskRepository;
    private final TaskHealthService healthService;

    @Transactional(readOnly = true)
    public List<TaskHealthResponse> getNonCompliantTasks() {
        return taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted())
                .map(t -> healthService.calculateTaskHealth(t.getId()))
                .filter(h -> h.isMissingAssignee() || h.isMissingEstimate() || h.isMissingDueDate())
                .collect(Collectors.toList());
    }
}
