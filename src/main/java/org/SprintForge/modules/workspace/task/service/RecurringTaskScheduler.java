package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecurringTaskScheduler {

    private final RecurringTaskService recurringTaskService;

    @Scheduled(cron = "0 */15 * * * *") // Runs every 15 minutes
    public void processRecurringTasks() {
        log.info("Starting background check for due recurring tasks...");
        try {
            recurringTaskService.executeDueRecurringTasks();
        } catch (Exception e) {
            log.error("Failed to execute due recurring tasks batch", e);
        }
    }
}
