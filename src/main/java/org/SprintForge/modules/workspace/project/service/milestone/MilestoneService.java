package org.SprintForge.modules.workspace.project.service.milestone;

import org.SprintForge.modules.workspace.project.dto.request.MilestoneCreateRequest;
import org.SprintForge.modules.workspace.project.dto.request.MilestoneUpdateRequest;
import org.SprintForge.modules.workspace.project.dto.response.MilestoneProgressResponse;
import org.SprintForge.modules.workspace.project.dto.response.MilestoneResponse;

import java.util.List;

/**
 * Business operations for managing Milestones within a Project.
 */
public interface MilestoneService {

    // --- Lifecycle ---

    MilestoneResponse createMilestone(Long projectId, MilestoneCreateRequest request, Long actorId);

    MilestoneResponse updateMilestone(Long milestoneId, MilestoneUpdateRequest request, Long actorId);

    MilestoneResponse archiveMilestone(Long milestoneId, Long actorId);

    void deleteMilestone(Long milestoneId, Long actorId);

    MilestoneResponse completeMilestone(Long milestoneId, Long actorId);

    // --- Task Assignment ---

    MilestoneResponse assignTask(Long milestoneId, Long taskId, Long actorId);

    MilestoneResponse removeTask(Long milestoneId, Long taskId, Long actorId);

    // --- Query ---

    List<MilestoneResponse> getProjectMilestones(Long projectId, Long actorId);

    List<MilestoneResponse> getOverdueMilestones(Long projectId, Long actorId);

    // --- Progress ---

    MilestoneProgressResponse calculateProgress(Long milestoneId, Long actorId);
}
