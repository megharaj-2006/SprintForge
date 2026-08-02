package org.SprintForge.modules.workspace.project.service.dashboard;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.dto.response.ProjectDashboardSummaryResponse;
import org.SprintForge.modules.workspace.project.entity.Milestone;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.entity.enums.MilestoneStatus;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.SprintForge.modules.workspace.project.repository.MilestoneRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectMemberRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRiskRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectDashboardServiceImpl implements ProjectDashboardService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;
    private final ProjectRiskRepository riskRepository;

    @Override
    @Transactional(readOnly = true)
    public ProjectDashboardSummaryResponse getDashboardSummary(Long projectId, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId);
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        int openTasks = totalTasks - completedTasks;
        double completionPct = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0.0;

        long teamSize = projectMemberRepository.countByProjectIdAndStatusAndIsDeletedFalse(projectId, ProjectMemberStatus.ACTIVE);
        long riskCount = riskRepository.countByProjectIdAndIsDeletedFalse(projectId);

        List<Milestone> milestones = milestoneRepository.findByProjectIdAndIsDeletedFalse(projectId);
        Optional<Milestone> nextMilestoneOpt = milestones.stream()
                .filter(m -> m.getDueDate() != null && m.getStatus() != MilestoneStatus.COMPLETED)
                .sorted((a, b) -> a.getDueDate().compareTo(b.getDueDate()))
                .findFirst();

        List<String> activities = new ArrayList<>();
        activities.add("Project " + project.getName() + " is currently " + project.getStatus());
        activities.add("Active team size: " + teamSize + " members");

        String healthStatus = "GOOD";
        if (riskCount > 5) {
            healthStatus = "CRITICAL";
        } else if (riskCount > 2) {
            healthStatus = "WARNING";
        }

        return ProjectDashboardSummaryResponse.builder()
                .projectId(project.getId())
                .projectName(project.getName())
                .projectKey(project.getProjectKey())
                .status(project.getStatus() != null ? project.getStatus().name() : "PLANNING")
                .completionPercentage(completionPct)
                .totalTasks(totalTasks)
                .openTasks(openTasks)
                .completedTasks(completedTasks)
                .velocity(project.getProgressPercentage() != null ? project.getProgressPercentage() : completionPct)
                .nextMilestoneId(nextMilestoneOpt.map(Milestone::getId).orElse(null))
                .nextMilestoneName(nextMilestoneOpt.map(Milestone::getName).orElse(null))
                .nextMilestoneDueDate(nextMilestoneOpt.map(Milestone::getDueDate).orElse(null))
                .upcomingReleasesCount(0)
                .riskCount((int) riskCount)
                .teamSize((int) teamSize)
                .healthStatus(healthStatus)
                .recentActivities(activities)
                .build();
    }
}
