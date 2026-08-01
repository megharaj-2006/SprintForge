package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.task.dto.response.EstimationAccuracyReportResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VarianceService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public EstimationAccuracyReportResponse calculateProjectAccuracy(Long projectId) {
        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId).stream()
                .filter(t -> t.getEstimatedHours() != null && t.getEstimatedHours() > 0)
                .collect(Collectors.toList());

        int total = tasks.size();
        if (total == 0) {
            return EstimationAccuracyReportResponse.builder()
                    .projectId(projectId)
                    .totalEstimatedTasks(0)
                    .averageEstimatedHours(0.0)
                    .averageActualHours(0.0)
                    .averageVariance(0.0)
                    .accuracyPercentage(100.0)
                    .overEstimatedCount(0)
                    .underEstimatedCount(0)
                    .accurateCount(0)
                    .build();
        }

        double totalEst = 0;
        double totalAct = 0;
        int over = 0;
        int under = 0;
        int accurate = 0;

        for (Task t : tasks) {
            double est = t.getEstimatedHours();
            double act = t.getActualHours() != null ? t.getActualHours() : est;
            totalEst += est;
            totalAct += act;

            double diff = act - est;
            if (Math.abs(diff) <= 0.5) {
                accurate++;
            } else if (diff < -0.5) {
                over++; // Estimated more than actual
            } else {
                under++; // Estimated less than actual
            }
        }

        double avgEst = totalEst / total;
        double avgAct = totalAct / total;
        double avgVar = (totalAct - totalEst) / total;
        double accuracyPct = ((double) accurate / total) * 100.0;

        return EstimationAccuracyReportResponse.builder()
                .projectId(projectId)
                .totalEstimatedTasks(total)
                .averageEstimatedHours(avgEst)
                .averageActualHours(avgAct)
                .averageVariance(avgVar)
                .accuracyPercentage(accuracyPct)
                .overEstimatedCount(over)
                .underEstimatedCount(under)
                .accurateCount(accurate)
                .build();
    }
}
