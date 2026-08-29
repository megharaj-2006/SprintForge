package org.SprintForge.platform.communication.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("platformEmailQueueClaimService")
@RequiredArgsConstructor
public class EmailQueueClaimService {

    private final EmailQueueRepository emailQueueRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<EmailQueue> claimPendingEmails(int batchSize) {
        List<EmailQueue> items = emailQueueRepository.claimPendingEmails(batchSize);
        List<EmailQueue> claimed = new ArrayList<>();
        for (EmailQueue item : items) {
            item.setStatus("PROCESSING");
            claimed.add(emailQueueRepository.save(item));
        }
        emailQueueRepository.flush();
        return claimed;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void completeEmailProcessing(Long emailId, boolean sent, String errorMessage) {
        EmailQueue item = emailQueueRepository.findById(emailId).orElse(null);
        if (item == null) return;

        if (sent) {
            item.setStatus("SENT");
            item.setSentAt(LocalDateTime.now());
            item.setError(null);
        } else {
            item.setAttempts(item.getAttempts() + 1);
            if (errorMessage != null) {
                item.setError(errorMessage);
            }
            if (item.getAttempts() >= 3) {
                item.setStatus("FAILED");
            } else {
                item.setStatus("PENDING");
            }
        }
        emailQueueRepository.save(item);
    }
}
