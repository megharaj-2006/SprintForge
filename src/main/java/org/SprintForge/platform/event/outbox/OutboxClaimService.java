package org.SprintForge.platform.event.outbox;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.event.metadata.EventStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service("platformOutboxClaimService")
@RequiredArgsConstructor
public class OutboxClaimService {

    private final OutboxEventRepository outboxEventRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<OutboxEvent> claimPendingEvents(int batchSize) {
        List<OutboxEvent> events = outboxEventRepository.claimPendingEvents(batchSize);
        List<OutboxEvent> claimed = new ArrayList<>();
        for (OutboxEvent outbox : events) {
            outbox.setStatus(EventStatus.PROCESSING);
            claimed.add(outboxEventRepository.save(outbox));
        }
        outboxEventRepository.flush();
        return claimed;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeOutboxEventSuccess(Long outboxId) {
        OutboxEvent outbox = outboxEventRepository.findById(outboxId).orElse(null);
        if (outbox == null) return;

        outbox.setStatus(EventStatus.COMPLETED);
        outbox.setProcessedAt(Instant.now());
        outbox.setLastError(null);
        outboxEventRepository.save(outbox);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeOutboxEventClassNotFound(Long outboxId, String error) {
        OutboxEvent outbox = outboxEventRepository.findById(outboxId).orElse(null);
        if (outbox == null) return;

        outbox.setStatus(EventStatus.FAILED);
        outbox.setLastError("Class not found: " + error);
        outboxEventRepository.save(outbox);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeOutboxEventFailure(Long outboxId, String error) {
        OutboxEvent outbox = outboxEventRepository.findById(outboxId).orElse(null);
        if (outbox == null) return;

        outbox.setRetryCount(outbox.getRetryCount() + 1);
        outbox.setLastError(error);
        if (outbox.getRetryCount() >= 3) {
            outbox.setStatus(EventStatus.FAILED);
        } else {
            outbox.setStatus(EventStatus.PENDING);
        }
        outboxEventRepository.save(outbox);
    }
}
