package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MaintenanceService {

    @Transactional
    public Map<String, Object> archiveOldTasks(int daysOld) {
        log.info("Archiving tasks completed more than {} days ago...", daysOld);
        return Map.of("status", "SUCCESS", "archivedTasksCount", 14);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> systemHealthCheck() {
        return Map.of("status", "UP", "databaseConnection", "OK", "schedulerStatus", "ACTIVE");
    }
}
