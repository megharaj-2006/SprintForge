package org.SprintForge.modules.workspace.project.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.epic.entity.Epic;
import org.SprintForge.modules.workspace.epic.repository.EpicRepository;
import org.SprintForge.modules.workspace.project.dto.response.RoadmapResponse;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoadmapService {

    private final ProjectRepository projectRepository;
    private final EpicRepository epicRepository;
    private final SprintRepository sprintRepository;

    @Transactional(readOnly = true)
    public RoadmapResponse getProjectRoadmap(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        List<Epic> epics = epicRepository.findByProjectIdAndIsDeletedFalse(projectId);
        List<Sprint> sprints = sprintRepository.findByProjectIdAndIsDeletedFalse(projectId);

        List<RoadmapResponse.RoadmapEpicItem> epicItems = epics.stream().map(e ->
                RoadmapResponse.RoadmapEpicItem.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .status(e.getStatus())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .progressPercentage(e.getProgressPercentage())
                        .build()
        ).collect(Collectors.toList());

        List<RoadmapResponse.RoadmapSprintItem> sprintItems = sprints.stream().map(s ->
                RoadmapResponse.RoadmapSprintItem.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .status(s.getStatus().name())
                        .startDate(s.getStartDate())
                        .endDate(s.getEndDate())
                        .build()
        ).collect(Collectors.toList());

        return RoadmapResponse.builder()
                .projectId(projectId)
                .projectName(project.getName())
                .epics(epicItems)
                .sprints(sprintItems)
                .build();
    }
}
