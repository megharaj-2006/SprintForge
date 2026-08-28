package org.SprintForge.platform.scheduler.controller;

import org.SprintForge.platform.scheduler.dto.CreateJobRequest;
import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.queue.JobQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformJobExecutionController")
@RequestMapping("/jobs")
@PreAuthorize("hasRole('ADMIN')")
public class JobExecutionController {

    private final JobQueueService jobQueueService;

    @Autowired
    public JobExecutionController(JobQueueService jobQueueService) {
        this.jobQueueService = jobQueueService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> submitJob(@RequestBody CreateJobRequest request) {
        return ResponseEntity.ok(jobQueueService.submitJob(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobQueueService.getJob(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<JobResponse> cancelJob(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobQueueService.cancelJob(id));
    }

    @GetMapping("/queued")
    public ResponseEntity<List<JobResponse>> getQueuedJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(jobQueueService.getQueuedJobs(page, size));
    }

    @GetMapping("/failed")
    public ResponseEntity<List<JobResponse>> getFailedJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(jobQueueService.getFailedJobs(page, size));
    }
}
