package org.SprintForge.modules.workspace.project.progress.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.goal.entity.Goal;
import org.SprintForge.modules.workspace.project.goal.repository.GoalRepository;
import org.SprintForge.modules.workspace.project.keyresult.entity.KeyResult;
import org.SprintForge.modules.workspace.project.keyresult.repository.KeyResultRepository;
import org.SprintForge.modules.workspace.project.objective.entity.Objective;
import org.SprintForge.modules.workspace.project.objective.repository.ObjectiveRepository;
import org.SprintForge.modules.workspace.project.progress.dto.GoalProgressResponse;
import org.SprintForge.modules.workspace.project.progress.dto.ProjectProgressResponse;
import org.SprintForge.modules.workspace.project.release.entity.Release;
import org.SprintForge.modules.workspace.project.release.repository.ReleaseRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressEngineServiceImpl implements ProgressEngineService {

    private final ProjectRepository projectRepository;
    private final GoalRepository goalRepository;
    private final ObjectiveRepository objectiveRepository;
    private final KeyResultRepository keyResultRepository;
    private final ReleaseRepository releaseRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public ProjectProgressResponse calculateProjectProgress(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<Goal> goals = goalRepository.findByProjectIdAndIsDeletedFalse(projectId);
        List<Release> releases = releaseRepository.findByProjectIdAndIsDeletedFalse(projectId);
        List<Task> tasks = taskRepository.findByProjectIdAndIsDeletedFalse(projectId);

        // 1. Tasks progress
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        double tasksProgress = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0.0;

        // 2. Goals progress
        double totalGoalWeightSum = 0.0;
        double weightedGoalProgressSum = 0.0;
        List<ProjectProgressResponse.GoalProgressDetail> goalDetails = new ArrayList<>();

        for (Goal goal : goals) {
            GoalProgressResponse goalProgress = calculateGoalProgress(goal.getId());
            double gWeight = goal.getWeight() != null ? goal.getWeight() : 1.0;
            totalGoalWeightSum += gWeight;
            weightedGoalProgressSum += (goalProgress.getProgressPercentage() * gWeight);

            goalDetails.add(ProjectProgressResponse.GoalProgressDetail.builder()
                    .goalId(goal.getId())
                    .title(goal.getTitle())
                    .progressPercentage(goalProgress.getProgressPercentage())
                    .weight(gWeight)
                    .build());
        }

        double goalsProgress = totalGoalWeightSum > 0 ? (weightedGoalProgressSum / totalGoalWeightSum) : tasksProgress;

        // 3. Releases progress
        double totalReleaseProgress = 0.0;
        if (!releases.isEmpty()) {
            for (Release rel : releases) {
                List<Task> relTasks = taskRepository.findByReleaseIdAndIsDeletedFalse(rel.getId());
                int relTotal = relTasks.size();
                int relDone = (int) relTasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
                double relProg = relTotal > 0 ? (relDone * 100.0 / relTotal) : 0.0;
                totalReleaseProgress += relProg;
            }
            totalReleaseProgress /= releases.size();
        } else {
            totalReleaseProgress = tasksProgress;
        }

        // Overall progress weighted combination
        double overallProgress = (goalsProgress * 0.5) + (totalReleaseProgress * 0.3) + (tasksProgress * 0.2);

        String healthScore = "GOOD";
        if (overallProgress >= 80.0) healthScore = "EXCELLENT";
        else if (overallProgress < 40.0) healthScore = "CRITICAL";
        else if (overallProgress < 60.0) healthScore = "AT_RISK";

        return ProjectProgressResponse.builder()
                .projectId(project.getId())
                .projectName(project.getName())
                .overallProgressPercentage(Math.min(100.0, Math.max(0.0, overallProgress)))
                .goalsProgressPercentage(goalsProgress)
                .releasesProgressPercentage(totalReleaseProgress)
                .tasksProgressPercentage(tasksProgress)
                .healthScore(healthScore)
                .totalGoals(goals.size())
                .totalReleases(releases.size())
                .totalTasks(totalTasks)
                .goalDetails(goalDetails)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public GoalProgressResponse calculateGoalProgress(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .filter(g -> !g.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with ID: " + goalId));

        List<Objective> objectives = objectiveRepository.findByGoalIdAndIsDeletedFalse(goalId);
        double totalObjectiveWeight = 0.0;
        double weightedObjectiveProgressSum = 0.0;
        int completedObjs = 0;
        List<GoalProgressResponse.ObjectiveProgressDetail> objDetails = new ArrayList<>();

        for (Objective obj : objectives) {
            double objProg = calculateObjectiveProgress(obj.getId());
            if (objProg >= 100.0) completedObjs++;

            double oWeight = obj.getWeight() != null ? obj.getWeight() : 1.0;
            totalObjectiveWeight += oWeight;
            weightedObjectiveProgressSum += (objProg * oWeight);

            long krCount = keyResultRepository.countByObjectiveIdAndIsDeletedFalse(obj.getId());

            objDetails.add(GoalProgressResponse.ObjectiveProgressDetail.builder()
                    .objectiveId(obj.getId())
                    .title(obj.getTitle())
                    .progressPercentage(objProg)
                    .weight(oWeight)
                    .totalKeyResults((int) krCount)
                    .build());
        }

        double goalProgress = totalObjectiveWeight > 0 ? (weightedObjectiveProgressSum / totalObjectiveWeight) : 0.0;

        return GoalProgressResponse.builder()
                .goalId(goal.getId())
                .title(goal.getTitle())
                .progressPercentage(Math.min(100.0, Math.max(0.0, goalProgress)))
                .status(goal.getStatus() != null ? goal.getStatus().name() : "DRAFT")
                .totalObjectives(objectives.size())
                .completedObjectives(completedObjs)
                .objectiveDetails(objDetails)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateObjectiveProgress(Long objectiveId) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Objective not found with ID: " + objectiveId));

        List<KeyResult> keyResults = keyResultRepository.findByObjectiveIdAndIsDeletedFalse(objectiveId);
        if (keyResults.isEmpty()) return 0.0;

        double totalKrWeight = 0.0;
        double weightedKrProgressSum = 0.0;

        for (KeyResult kr : keyResults) {
            double krProgress = kr.getTargetValue() > 0 ? Math.min(100.0, (kr.getCurrentValue() / kr.getTargetValue()) * 100.0) : 0.0;
            double krWeight = kr.getWeight() != null ? kr.getWeight() : 1.0;
            totalKrWeight += krWeight;
            weightedKrProgressSum += (krProgress * krWeight);
        }

        return totalKrWeight > 0 ? Math.min(100.0, Math.max(0.0, weightedKrProgressSum / totalKrWeight)) : 0.0;
    }
}
