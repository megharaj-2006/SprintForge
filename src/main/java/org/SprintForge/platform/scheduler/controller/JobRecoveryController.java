package org.SprintForge.platform.scheduler.controller;

import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.retry.RecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformJobRecoveryController")
@RequestMapping("/jobs")
@PreAuthorize("hasRole('ADMIN')")
public class JobRecoveryController {

    private final RecoveryService recoveryService;

    @Autowired
    public JobRecoveryController(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<JobResponse> retryJob(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recoveryService.retryJob(id));
    }

    @PostMapping("/{id}/recover")
    public ResponseEntity<JobResponse> recoverDeadJob(@PathVariable("id") Long id) {
        return ResponseEntity.ok(recoveryService.recoverDeadJob(id));
    }

    @GetMapping("/dead")
    public ResponseEntity<List<JobResponse>> getDeadJobs(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(recoveryService.getDeadQueueJobs(page, size));
    }
}
