package org.SprintForge.platform.communication.email;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailQueueProcessor {

    private final EmailQueueService emailQueueService;

    @Scheduled(fixedDelay = 5000)
    public void runQueueProcessor() {
        emailQueueService.processQueue();
    }
}
