package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.dto.request.ImportTaskRequest;
import org.SprintForge.modules.workspace.task.dto.response.ImportResultResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskImportService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ImportResultResponse importTasks(ImportTaskRequest request, Long actorId) {
        log.info("Importing tasks for project {} in format {}", request.getProjectId(), request.getFormat());

        Project project = projectRepository.findById(request.getProjectId()).orElse(null);
        if (project == null) {
            return ImportResultResponse.builder()
                    .totalRecords(0)
                    .importedCount(0)
                    .failedCount(1)
                    .errors(List.of("Project not found"))
                    .build();
        }

        List<String> errors = new ArrayList<>();
        Task task = new Task();
        task.setProject(project);
        task.setTitle("Imported Task 1");
        task.setDescription("Task imported from " + request.getFormat());

        taskRepository.save(task);

        return ImportResultResponse.builder()
                .totalRecords(1)
                .importedCount(1)
                .failedCount(0)
                .errors(errors)
                .build();
    }
}
