package org.SprintForge.platform.scheduler.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.platform.scheduler.domain.BatchJob;
import org.SprintForge.platform.scheduler.domain.JobStatus;
import org.SprintForge.platform.scheduler.domain.JobType;
import org.SprintForge.platform.scheduler.repository.BatchJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service("platformBatchProcessingService")
public class BatchProcessingService {

    private static final Logger log = LoggerFactory.getLogger(BatchProcessingService.class);
    private final BatchJobRepository batchJobRepository;

    @Autowired
    public BatchProcessingService(BatchJobRepository batchJobRepository) {
        this.batchJobRepository = batchJobRepository;
    }

    @Transactional
    public BatchJob createBatchJob(String name, JobType jobType, int totalItems) {
        BatchJob batchJob = BatchJob.builder()
                .name(name)
                .jobType(jobType)
                .totalItems(totalItems)
                .status(JobStatus.RUNNING)
                .startedAt(Instant.now())
                .build();

        return batchJobRepository.save(batchJob);
    }

    @Transactional
    public BatchJob updateProgress(Long batchJobId, int processedChunk, int failedChunk) {
        BatchJob batch = batchJobRepository.findById(batchJobId)
                .orElseThrow(() -> new IllegalArgumentException("BatchJob not found: " + batchJobId));

        batch.setProcessedItems(batch.getProcessedItems() + processedChunk);
        batch.setFailedItems(batch.getFailedItems() + failedChunk);

        if (batch.getProcessedItems() + batch.getFailedItems() >= batch.getTotalItems()) {
            batch.setStatus(JobStatus.COMPLETED);
            batch.setCompletedAt(Instant.now());
        }

        return batchJobRepository.save(batch);
    }

    public List<BatchJob> getAllBatchJobs() {
        return batchJobRepository.findAll();
    }
}
