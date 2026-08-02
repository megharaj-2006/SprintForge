package org.SprintForge.modules.workspace.project.roadmap.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Milestone;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.goal.entity.Goal;
import org.SprintForge.modules.workspace.project.goal.repository.GoalRepository;
import org.SprintForge.modules.workspace.project.release.entity.Release;
import org.SprintForge.modules.workspace.project.release.repository.ReleaseRepository;
import org.SprintForge.modules.workspace.project.repository.MilestoneRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.project.roadmap.dto.RoadmapItemResponse;
import org.SprintForge.modules.workspace.project.roadmap.dto.StrategicRoadmapResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoadmapServiceImpl implements RoadmapService {

    private final ProjectRepository projectRepository;
    private final GoalRepository goalRepository;
    private final ReleaseRepository releaseRepository;
    private final MilestoneRepository milestoneRepository;

    @Override
    @Transactional(readOnly = true)
    public StrategicRoadmapResponse getProjectRoadmap(Long projectId, String timeframe, String viewMode) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        String selectedTimeframe = (timeframe != null && !timeframe.isBlank()) ? timeframe.toUpperCase() : "QUARTERLY";
        String selectedViewMode = (viewMode != null && !viewMode.isBlank()) ? viewMode.toUpperCase() : "TIMELINE";

        List<RoadmapItemResponse> items = new ArrayList<>();

        // Add Goals
        List<Goal> goals = goalRepository.findByProjectIdAndIsDeletedFalse(projectId);
        for (Goal goal : goals) {
            items.add(RoadmapItemResponse.builder()
                    .id(goal.getId())
                    .itemType("GOAL")
                    .title(goal.getTitle())
                    .description(goal.getDescription())
                    .status(goal.getStatus() != null ? goal.getStatus().name() : "DRAFT")
                    .progressPercentage(0.0)
                    .startDate(goal.getStartDate())
                    .endDate(goal.getTargetDate())
                    .color("#8B5CF6")
                    .timeBucket(goal.getStartDate() != null ? "Q" + ((goal.getStartDate().getMonthValue() - 1) / 3 + 1) + " " + goal.getStartDate().getYear() : "UNSCHEDULED")
                    .build());
        }

        // Add Releases
        List<Release> releases = releaseRepository.findByProjectIdAndIsDeletedFalse(projectId);
        for (Release release : releases) {
            items.add(RoadmapItemResponse.builder()
                    .id(release.getId())
                    .itemType("RELEASE")
                    .title(release.getName() + " (" + release.getVersion() + ")")
                    .description(release.getDescription())
                    .status(release.getStatus() != null ? release.getStatus().name() : "PLANNING")
                    .progressPercentage(0.0)
                    .startDate(release.getPlannedStart())
                    .endDate(release.getPlannedReleaseDate())
                    .color(release.getColor() != null ? release.getColor() : "#3B82F6")
                    .timeBucket(release.getPlannedReleaseDate() != null ? "Q" + ((release.getPlannedReleaseDate().getMonthValue() - 1) / 3 + 1) + " " + release.getPlannedReleaseDate().getYear() : "UNSCHEDULED")
                    .build());
        }

        // Add Milestones
        List<Milestone> milestones = milestoneRepository.findByProjectIdAndIsDeletedFalse(projectId);
        for (Milestone milestone : milestones) {
            items.add(RoadmapItemResponse.builder()
                    .id(milestone.getId())
                    .itemType("MILESTONE")
                    .title(milestone.getName())
                    .description(milestone.getDescription())
                    .status(milestone.getStatus() != null ? milestone.getStatus().name() : "ACTIVE")
                    .progressPercentage(milestone.getProgressPercentage() != null ? milestone.getProgressPercentage() : 0.0)
                    .startDate(milestone.getDueDate())
                    .endDate(milestone.getDueDate())
                    .color("#10B981")
                    .timeBucket(milestone.getDueDate() != null ? "Q" + ((milestone.getDueDate().getMonthValue() - 1) / 3 + 1) + " " + milestone.getDueDate().getYear() : "UNSCHEDULED")
                    .build());
        }

        return StrategicRoadmapResponse.builder()
                .projectId(project.getId())
                .projectName(project.getName())
                .timeframe(selectedTimeframe)
                .viewMode(selectedViewMode)
                .items(items)
                .build();
    }
}
