package org.SprintForge.modules.workspace.project.release.service;

import org.SprintForge.modules.workspace.project.release.dto.request.CreateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.request.UpdateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseProgressResponse;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseResponse;

import java.util.List;

public interface ReleaseService {
    ReleaseResponse createRelease(Long projectId, CreateReleaseRequest request, Long actorId);
    ReleaseResponse updateRelease(Long releaseId, UpdateReleaseRequest request, Long actorId);
    List<ReleaseResponse> getReleases(Long projectId);
    ReleaseResponse getRelease(Long releaseId);
    void deleteRelease(Long releaseId, Long actorId);
    ReleaseResponse publishRelease(Long releaseId, Long actorId);
    ReleaseResponse cloneRelease(Long releaseId, String newVersion, Long actorId);
    void assignTaskToRelease(Long releaseId, Long taskId, Long actorId);
    ReleaseProgressResponse getReleaseProgress(Long releaseId);
}
