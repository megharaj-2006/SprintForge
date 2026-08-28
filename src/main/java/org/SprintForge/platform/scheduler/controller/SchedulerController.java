package org.SprintForge.platform.scheduler.controller;

import org.SprintForge.platform.scheduler.domain.ScheduledJob;
import org.SprintForge.platform.scheduler.dto.JobResponse;
import org.SprintForge.platform.scheduler.dto.ScheduleJobRequest;
import org.SprintForge.platform.scheduler.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformSchedulerController")
@RequestMapping("/scheduler")
@PreAuthorize("hasRole('ADMIN')")
public class SchedulerController {

    private final SchedulerService schedulerService;

    @Autowired
    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/jobs")
    public ResponseEntity<ScheduledJob> createScheduledJob(@RequestBody ScheduleJobRequest request) {
        return ResponseEntity.ok(schedulerService.createScheduledJob(request));
    }

    @PostMapping("/jobs/one-time")
    public ResponseEntity<JobResponse> scheduleOneTimeJob(@RequestBody ScheduleJobRequest request) {
        return ResponseEntity.ok(schedulerService.scheduleOneTimeJob(request));
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<ScheduledJob>> getAllScheduledJobs() {
        return ResponseEntity.ok(schedulerService.getAllScheduledJobs());
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteScheduledJob(@PathVariable("id") Long id) {
        schedulerService.deleteScheduledJob(id);
        return ResponseEntity.noContent().build();
    }
}
