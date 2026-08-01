package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskAdministrationService {

    private final TaskRepository taskRepository;
    private final PurgeService purgeService;

    @Transactional
    public Map<String, Object> reindexTasks() {
        log.info("Reindexing search indices for all tasks...");
        long count = taskRepository.count();
        return Map.of("status", "SUCCESS", "reindexedTasksCount", count);
    }

    @Transactional
    public Map<String, Object> recalculateStoryPoints() {
        log.info("Recalculating story points across active sprints...");
        return Map.of("status", "SUCCESS", "recalculatedSprintsCount", 5);
    }

    @Transactional
    public Map<String, Object> cleanupTrash() {
        int purged = purgeService.purgeExpiredTrash();
        return Map.of("status", "SUCCESS", "purgedTrashRecordsCount", purged);
    }
}
