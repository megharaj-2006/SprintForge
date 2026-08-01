package org.SprintForge.modules.workspace.project.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.user.entity.UserCapacity;
import org.SprintForge.modules.user.service.CapacityService;
import org.SprintForge.modules.workspace.project.dto.response.WorkloadReportResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.TaskAssignment;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkloadService {

    private final TaskRepository taskRepository;
    private final CapacityService capacityService;

    @Transactional(readOnly = true)
    public WorkloadReportResponse getProjectWorkload(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId).stream()
                .filter(t -> t.getStatus() != TaskStatus.DONE)
                .collect(Collectors.toList());

        Map<Long, Double> userHoursMap = new HashMap<>();
        Map<Long, Integer> userTaskCountMap = new HashMap<>();

        for (Task t : tasks) {
            double hrs = t.getEstimatedHours() != null ? t.getEstimatedHours() : 0.0;
            for (TaskAssignment a : t.getAssignments()) {
                Long uid = a.getProjectMember() != null ? a.getProjectMember().getWorkspaceMemberId() : 1L;
                userHoursMap.put(uid, userHoursMap.getOrDefault(uid, 0.0) + hrs);
                userTaskCountMap.put(uid, userTaskCountMap.getOrDefault(uid, 0) + 1);
            }
        }

        List<WorkloadReportResponse.UserWorkloadSummary> summaries = new ArrayList<>();
        for (Map.Entry<Long, Double> entry : userHoursMap.entrySet()) {
            Long uid = entry.getKey();
            Double assignedHrs = entry.getValue();
            int count = userTaskCountMap.getOrDefault(uid, 0);

            UserCapacity cap = capacityService.getUserCapacity(uid);
            double weeklyCap = cap.getWeeklyHoursCapacity();
            double remaining = weeklyCap - assignedHrs;
            boolean isOverloaded = assignedHrs > weeklyCap;

            summaries.add(WorkloadReportResponse.UserWorkloadSummary.builder()
                    .userId(uid)
                    .assignedTaskCount(count)
                    .totalAssignedHours(assignedHrs)
                    .weeklyCapacityHours(weeklyCap)
                    .remainingCapacityHours(remaining)
                    .isOverloaded(isOverloaded)
                    .isOnVacation(cap.getIsOnVacation())
                    .build());
        }

        return WorkloadReportResponse.builder()
                .projectId(projectId)
                .totalTeamMembers(summaries.size())
                .memberWorkloads(summaries)
                .build();
    }

    @Transactional(readOnly = true)
    public List<WorkloadReportResponse.UserWorkloadSummary> getOverloadedUsers(Long projectId) {
        WorkloadReportResponse report = getProjectWorkload(projectId);
        return report.getMemberWorkloads().stream()
                .filter(WorkloadReportResponse.UserWorkloadSummary::isOverloaded)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkloadReportResponse.UserWorkloadSummary> getBusyUsers(Long projectId) {
        WorkloadReportResponse report = getProjectWorkload(projectId);
        return report.getMemberWorkloads().stream()
                .sorted((a, b) -> Double.compare(b.getTotalAssignedHours(), a.getTotalAssignedHours()))
                .collect(Collectors.toList());
    }
}
