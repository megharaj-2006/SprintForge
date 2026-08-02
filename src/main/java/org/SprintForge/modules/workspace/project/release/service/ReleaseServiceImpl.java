package org.SprintForge.modules.workspace.project.release.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ConflictException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.project.entity.Project;
import org.SprintForge.modules.workspace.project.release.dto.request.CreateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.request.UpdateReleaseRequest;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseProgressResponse;
import org.SprintForge.modules.workspace.project.release.dto.response.ReleaseResponse;
import org.SprintForge.modules.workspace.project.release.entity.Release;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseStatus;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseType;
import org.SprintForge.modules.workspace.project.release.event.ReleaseArchivedEvent;
import org.SprintForge.modules.workspace.project.release.event.ReleaseCreatedEvent;
import org.SprintForge.modules.workspace.project.release.event.ReleasePublishedEvent;
import org.SprintForge.modules.workspace.project.release.repository.ReleaseRepository;
import org.SprintForge.modules.workspace.project.repository.ProjectRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReleaseServiceImpl implements ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ReleaseResponse createRelease(Long projectId, CreateReleaseRequest request, Long actorId) {
        Project project = projectRepository.findById(projectId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));

        if (releaseRepository.existsByProjectIdAndReleaseVersionAndIsDeletedFalse(projectId, request.getVersion())) {
            throw new ConflictException("Release version '" + request.getVersion() + "' already exists in this project.");
        }

        Release release = new Release();
        release.setProjectId(projectId);
        release.setName(request.getName());
        release.setReleaseVersion(request.getVersion());
        release.setDescription(request.getDescription());
        release.setReleaseType(request.getReleaseType() != null ? request.getReleaseType() : ReleaseType.MINOR);
        release.setStatus(ReleaseStatus.PLANNING);
        release.setPlannedStart(request.getPlannedStart());
        release.setPlannedReleaseDate(request.getPlannedReleaseDate());
        release.setOwnerId(request.getOwnerId() != null ? request.getOwnerId() : actorId);
        release.setReleaseNotes(request.getReleaseNotes());
        release.setColor(request.getColor() != null ? request.getColor() : "#3B82F6");

        Release saved = releaseRepository.save(release);
        eventPublisher.publishEvent(new ReleaseCreatedEvent(saved.getId(), projectId, actorId, LocalDateTime.now()));

        return toResponse(saved);
    }

    @Override
    @Transactional
    public ReleaseResponse updateRelease(Long releaseId, UpdateReleaseRequest request, Long actorId) {
        Release release = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));

        if (release.getStatus() == ReleaseStatus.RELEASED) {
            throw new BusinessRuleException("Cannot modify a released version.");
        }

        if (request.getName() != null) release.setName(request.getName());
        if (request.getDescription() != null) release.setDescription(request.getDescription());
        if (request.getReleaseType() != null) release.setReleaseType(request.getReleaseType());
        if (request.getStatus() != null) release.setStatus(request.getStatus());
        if (request.getPlannedStart() != null) release.setPlannedStart(request.getPlannedStart());
        if (request.getPlannedReleaseDate() != null) release.setPlannedReleaseDate(request.getPlannedReleaseDate());
        if (request.getActualReleaseDate() != null) release.setActualReleaseDate(request.getActualReleaseDate());
        if (request.getOwnerId() != null) release.setOwnerId(request.getOwnerId());
        if (request.getReleaseNotes() != null) release.setReleaseNotes(request.getReleaseNotes());
        if (request.getColor() != null) release.setColor(request.getColor());

        Release saved = releaseRepository.save(release);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReleaseResponse> getReleases(Long projectId) {
        return releaseRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReleaseResponse getRelease(Long releaseId) {
        Release release = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));
        return toResponse(release);
    }

    @Override
    @Transactional
    public void deleteRelease(Long releaseId, Long actorId) {
        Release release = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));

        release.markDeleted(actorId != null ? actorId.toString() : "SYSTEM");
        releaseRepository.save(release);
        eventPublisher.publishEvent(new ReleaseArchivedEvent(releaseId, release.getProjectId(), actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional
    public ReleaseResponse publishRelease(Long releaseId, Long actorId) {
        Release release = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));

        release.setStatus(ReleaseStatus.RELEASED);
        release.setActualReleaseDate(LocalDate.now());
        Release saved = releaseRepository.save(release);

        eventPublisher.publishEvent(new ReleasePublishedEvent(releaseId, release.getProjectId(), release.getReleaseVersion(), actorId, LocalDateTime.now()));
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ReleaseResponse cloneRelease(Long releaseId, String newVersion, Long actorId) {
        Release source = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));

        String targetVersion = (newVersion != null && !newVersion.isBlank()) ? newVersion : source.getReleaseVersion() + "-COPY";
        if (releaseRepository.existsByProjectIdAndReleaseVersionAndIsDeletedFalse(source.getProjectId(), targetVersion)) {
            throw new ConflictException("Release version '" + targetVersion + "' already exists in this project.");
        }

        Release cloned = new Release();
        cloned.setProjectId(source.getProjectId());
        cloned.setName("Copy of " + source.getName());
        cloned.setReleaseVersion(targetVersion);
        cloned.setDescription(source.getDescription());
        cloned.setReleaseType(source.getReleaseType());
        cloned.setStatus(ReleaseStatus.PLANNING);
        cloned.setColor(source.getColor());

        Release saved = releaseRepository.save(cloned);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void assignTaskToRelease(Long releaseId, Long taskId, Long actorId) {
        Release release = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));

        Task task = taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        if (!task.getProject().getId().equals(release.getProjectId())) {
            throw new BusinessRuleException("Task belongs to a different project than the release.");
        }

        task.setReleaseId(releaseId);
        taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public ReleaseProgressResponse getReleaseProgress(Long releaseId) {
        Release release = releaseRepository.findById(releaseId)
                .filter(r -> !r.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Release not found with ID: " + releaseId));

        List<Task> tasks = taskRepository.findByReleaseIdAndIsDeletedFalse(releaseId);
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        int openTasks = totalTasks - completedTasks;
        double progress = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0.0;

        Long daysRemaining = null;
        if (release.getPlannedReleaseDate() != null) {
            daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), release.getPlannedReleaseDate());
        }

        return ReleaseProgressResponse.builder()
                .releaseId(release.getId())
                .releaseName(release.getName())
                .version(release.getReleaseVersion())
                .status(release.getStatus().name())
                .progressPercentage(progress)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .openTasks(openTasks)
                .totalSprints(0)
                .totalMilestones(0)
                .plannedReleaseDate(release.getPlannedReleaseDate())
                .daysRemaining(daysRemaining)
                .build();
    }

    private ReleaseResponse toResponse(Release release) {
        List<Task> tasks = taskRepository.findByReleaseIdAndIsDeletedFalse(release.getId());
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count();
        double progress = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0.0;

        Long creatorId = null;
        if (release.getCreatedBy() != null) {
            try {
                creatorId = Long.parseLong(release.getCreatedBy());
            } catch (NumberFormatException ignored) {}
        }

        return ReleaseResponse.builder()
                .id(release.getId())
                .projectId(release.getProjectId())
                .name(release.getName())
                .version(release.getReleaseVersion())
                .description(release.getDescription())
                .releaseType(release.getReleaseType())
                .status(release.getStatus())
                .plannedStart(release.getPlannedStart())
                .plannedReleaseDate(release.getPlannedReleaseDate())
                .actualReleaseDate(release.getActualReleaseDate())
                .ownerId(release.getOwnerId())
                .releaseNotes(release.getReleaseNotes())
                .color(release.getColor())
                .progressPercentage(progress)
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .createdBy(creatorId)
                .createdAt(release.getCreatedAt())
                .updatedAt(release.getUpdatedAt())
                .build();
    }
}
