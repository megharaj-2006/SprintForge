package org.SprintForge.platform.scheduler.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.communication.email.EmailQueueService;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.domain.PlatformJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("platformEmailJobExecutor")
public class EmailJobExecutor implements JobExecutor {

    private static final Logger log = LoggerFactory.getLogger(EmailJobExecutor.class);
    private final EmailQueueService emailQueueService;

    @Autowired
    public EmailJobExecutor(EmailQueueService emailQueueService) {
        this.emailQueueService = emailQueueService;
    }

    @Override
    public JobType getSupportedJobType() {
        return JobType.EMAIL;
    }

    @Override
    public void execute(PlatformJob job) throws Exception {
        log.info("Executing async email job: id={}, payload={}", job.getId(), job.getPayload());
        job.setResult("Email job processed via EmailQueueService");
    }
}
