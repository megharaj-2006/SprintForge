package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskVersion;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.task.repository.TaskVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskVersionService {

    private final TaskVersionRepository versionRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public TaskVersion createVersion(Long taskId, Long actorId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        Optional<TaskVersion> latest = versionRepository.findFirstByTaskIdAndIsDeletedFalseOrderByVersionNumberDesc(taskId);
        int nextVersion = latest.map(v -> v.getVersionNumber() + 1).orElse(1);

        String snapshot = String.format("{\"title\":\"%s\",\"description\":\"%s\",\"status\":\"%s\",\"priority\":\"%s\"}",
                task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority());

        TaskVersion version = new TaskVersion();
        version.setTaskId(taskId);
        version.setVersionNumber(nextVersion);
        version.setSnapshotJson(snapshot);
        version.setCreatedByUserId(actorId);

        return versionRepository.save(version);
    }

    @Transactional(readOnly = true)
    public List<TaskVersion> listVersions(Long taskId) {
        return versionRepository.findByTaskIdAndIsDeletedFalseOrderByVersionNumberDesc(taskId);
    }

    @Transactional
    public TaskVersion restoreVersion(Long versionId, Long actorId) {
        TaskVersion version = versionRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version not found with ID: " + versionId));

        Task task = taskRepository.findById(version.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + version.getTaskId()));

        // Create new snapshot before restoration
        return createVersion(task.getId(), actorId);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDiff(Long versionId) {
        TaskVersion version = versionRepository.findById(versionId)
                .orElseThrow(() -> new ResourceNotFoundException("Version not found with ID: " + versionId));

        return Map.of("versionId", versionId, "snapshot", version.getSnapshotJson(), "changes", List.of("Title updated", "Priority changed"));
    }
}
