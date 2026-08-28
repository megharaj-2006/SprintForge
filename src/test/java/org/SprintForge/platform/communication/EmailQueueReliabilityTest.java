package org.SprintForge.platform.communication;

import org.SprintForge.platform.communication.email.EmailQueue;
import org.SprintForge.platform.communication.email.EmailQueueClaimService;
import org.SprintForge.platform.communication.email.EmailQueueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class EmailQueueReliabilityTest {

    @Autowired
    private EmailQueueClaimService emailQueueClaimService;

    @Autowired
    private EmailQueueRepository emailQueueRepository;

    @Test
    @DisplayName("Claimed email enters PROCESSING status upon claim")
    public void testAtomicEmailClaiming() {
        EmailQueue email = EmailQueue.builder()
                .recipient("test@sprintforge.internal")
                .templateName("TEST_TEMPLATE")
                .payload("{}")
                .status("PENDING")
                .attempts(0)
                .scheduledAt(Instant.now())
                .build();

        EmailQueue saved = emailQueueRepository.save(email);

        List<EmailQueue> claimed = emailQueueClaimService.claimPendingEmails(10);

        assertThat(claimed).isNotEmpty();
        EmailQueue claimedEmail = emailQueueRepository.findById(saved.getId()).orElse(null);
        assertThat(claimedEmail).isNotNull();
        assertThat(claimedEmail.getStatus()).isEqualTo("PROCESSING");

        // Cleanup
        emailQueueRepository.deleteById(saved.getId());
    }
}
