package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.task.dto.request.ExportTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.ExportJobResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskExportService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public ExportJobResponse exportTasks(ExportTaskRequest request, Long actorId) {
        log.info("Exporting tasks for project {} in format {}", request.getProjectId(), request.getFormat());

        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(request.getProjectId());
        String jobId = UUID.randomUUID().toString();

        return ExportJobResponse.builder()
                .jobId(jobId)
                .status("COMPLETED")
                .downloadUrl("/api/v1/exports/" + jobId + "/download")
                .format(request.getFormat())
                .exportedTasksCount(tasks.size())
                .build();
    }
}
