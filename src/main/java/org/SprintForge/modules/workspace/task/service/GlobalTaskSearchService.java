package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskPriority;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.mapper.TaskMapper;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.specification.TaskSearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GlobalTaskSearchService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<TaskResponse> globalSearch(String query, Long projectId, TaskStatus status, TaskPriority priority, Long sprintId, Boolean isOverdue, int page, int size) {
        Page<Task> tasks = taskRepository.findAll(
                TaskSearchSpecification.searchTasks(query, projectId, status, priority, sprintId, isOverdue),
                PageRequest.of(page, size)
        );
        return tasks.getContent().stream().map(taskMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getSearchSuggestions(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        List<Task> tasks = taskRepository.findAll(
                TaskSearchSpecification.searchTasks(query, null, null, null, null, false),
                PageRequest.of(0, 10)
        ).getContent();

        return tasks.stream().map(Task::getTitle).collect(Collectors.toList());
    }
}
