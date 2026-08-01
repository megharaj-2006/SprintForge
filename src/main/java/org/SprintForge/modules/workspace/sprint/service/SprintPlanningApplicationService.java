package org.SprintForge.modules.workspace.sprint.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.InvalidOperationException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintCompleteRequest;
import org.SprintForge.modules.workspace.sprint.dto.request.SprintStartRequest;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintBurndownResponse;
import org.SprintForge.modules.workspace.sprint.dto.response.SprintResponse;
import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.SprintForge.modules.workspace.sprint.event.SprintArchivedEvent;
import org.SprintForge.modules.workspace.sprint.event.SprintCompletedEvent;
import org.SprintForge.modules.workspace.sprint.event.SprintStartedEvent;
import org.SprintForge.modules.workspace.sprint.event.TaskMovedToSprintEvent;
import org.SprintForge.modules.workspace.sprint.repository.SprintRepository;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.TaskStatus;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SprintPlanningApplicationService {

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public SprintResponse startSprint(Long sprintId, SprintStartRequest request, Long actorId) {
        log.info("Starting sprint {} by user {}", sprintId, actorId);

        Sprint sprint = findSprintOrThrow(sprintId);

        if (sprint.getStatus() == SprintStatus.ACTIVE) {
            throw new InvalidOperationException("Sprint is already active");
        }
        if (sprint.getStatus() == SprintStatus.COMPLETED || sprint.getStatus() == SprintStatus.ARCHIVED) {
            throw new InvalidOperationException("Closed or archived sprint cannot be started");
        }

        boolean hasActiveSprint = sprintRepository.existsByProjectIdAndStatusAndIsDeletedFalse(sprint.getProjectId(), SprintStatus.ACTIVE);
        if (hasActiveSprint) {
            throw new BusinessRuleException("Only one active sprint is allowed per project");
        }

        if (request != null) {
            if (request.getStartDate() != null) sprint.setStartDate(request.getStartDate());
            if (request.getEndDate() != null) sprint.setEndDate(request.getEndDate());
            if (request.getGoal() != null) sprint.setGoal(request.getGoal());
        }

        if (sprint.getStartDate() == null) sprint.setStartDate(LocalDate.now());
        if (sprint.getEndDate() == null) sprint.setEndDate(LocalDate.now().plusWeeks(2));

        sprint.setStatus(SprintStatus.ACTIVE);
        Sprint saved = sprintRepository.save(sprint);

        eventPublisher.publishEvent(new SprintStartedEvent(sprintId, sprint.getProjectId(), actorId));
        return mapToResponse(saved);
    }

    @Transactional
    public SprintResponse completeSprint(Long sprintId, SprintCompleteRequest request, Long actorId) {
        log.info("Completing sprint {} by user {}", sprintId, actorId);

        Sprint sprint = findSprintOrThrow(sprintId);

        if (sprint.getStatus() != SprintStatus.ACTIVE) {
            throw new InvalidOperationException("Only ACTIVE sprints can be completed");
        }

        List<Task> sprintTasks = taskRepository.findBySprintIdAndIsDeletedFalse(sprintId);
        List<Task> completedTasks = sprintTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE)
                .collect(Collectors.toList());
        List<Task> uncompletedTasks = sprintTasks.stream()
                .filter(t -> t.getStatus() != TaskStatus.DONE)
                .collect(Collectors.toList());

        int completedSp = completedTasks.stream()
                .mapToInt(t -> t.getStoryPoints() != null ? t.getStoryPoints() : 0)
                .sum();

        sprint.setStatus(SprintStatus.COMPLETED);
        sprint.setCompletedAt(LocalDateTime.now());
        sprint.setCompletedStoryPoints(completedSp);
        sprint.setCompletedTaskCount(completedTasks.size());
        sprint.setTotalTaskCount(sprintTasks.size());
        sprint.setVelocity((double) completedSp);

        Long targetSprintId = request != null ? request.getMoveRemainingTasksToSprintId() : null;
        Sprint targetSprint = null;
        if (targetSprintId != null) {
            targetSprint = findSprintOrThrow(targetSprintId);
        }

        for (Task uncompleted : uncompletedTasks) {
            uncompleted.setSprint(targetSprint);
        }
        if (!uncompletedTasks.isEmpty()) {
            taskRepository.saveAll(uncompletedTasks);
        }

        Sprint saved = sprintRepository.save(sprint);
        eventPublisher.publishEvent(new SprintCompletedEvent(sprintId, sprint.getProjectId(), completedTasks.size(), uncompletedTasks.size(), actorId));

        return mapToResponse(saved);
    }

    @Transactional
    public SprintResponse archiveSprint(Long sprintId, Long actorId) {
        Sprint sprint = findSprintOrThrow(sprintId);
        sprint.setStatus(SprintStatus.ARCHIVED);
        sprint.setArchivedAt(LocalDateTime.now());

        Sprint saved = sprintRepository.save(sprint);
        eventPublisher.publishEvent(new SprintArchivedEvent(sprintId, actorId));
        return mapToResponse(saved);
    }

    @Transactional
    public SprintResponse cloneSprint(Long sprintId, Long actorId) {
        Sprint source = findSprintOrThrow(sprintId);

        Sprint clone = new Sprint();
        clone.setProjectId(source.getProjectId());
        clone.setName(source.getName() + " (Copy)");
        clone.setGoal(source.getGoal());
        clone.setStatus(SprintStatus.PLANNED);
        clone.setStartDate(LocalDate.now());
        clone.setEndDate(LocalDate.now().plusWeeks(2));
        clone.setPlannedStoryPoints(0);
        clone.setCompletedStoryPoints(0);
        clone.setVelocity(0.0);
        clone.setCapacity(source.getCapacity());
        clone.setOrderIndex((int) sprintRepository.countByProjectIdAndIsDeletedFalse(source.getProjectId()) + 1);

        Sprint saved = sprintRepository.save(clone);
        return mapToResponse(saved);
    }

    @Transactional
    public SprintResponse moveTasksToSprint(Long sprintId, List<Long> taskIds, Long actorId) {
        Sprint sprint = findSprintOrThrow(sprintId);
        if (sprint.getStatus() == SprintStatus.COMPLETED || sprint.getStatus() == SprintStatus.ARCHIVED) {
            throw new InvalidOperationException("Cannot add tasks to a completed or archived sprint");
        }

        List<Task> tasks = taskRepository.findAllById(taskIds);
        for (Task t : tasks) {
            t.setSprint(sprint);
        }
        taskRepository.saveAll(tasks);

        eventPublisher.publishEvent(new TaskMovedToSprintEvent(taskIds, sprintId, actorId));
        return mapToResponse(sprint);
    }

    @Transactional(readOnly = true)
    public SprintBurndownResponse getBurndown(Long sprintId) {
        Sprint sprint = findSprintOrThrow(sprintId);
        List<Task> tasks = taskRepository.findBySprintIdAndIsDeletedFalse(sprintId);

        int totalSp = tasks.stream().mapToInt(t -> t.getStoryPoints() != null ? t.getStoryPoints() : 0).sum();
        LocalDate start = sprint.getStartDate() != null ? sprint.getStartDate() : LocalDate.now();
        LocalDate end = sprint.getEndDate() != null ? sprint.getEndDate() : start.plusWeeks(2);

        long days = Math.max(1, start.datesUntil(end.plusDays(1)).count());
        double dailyIdealDrop = (double) totalSp / (days - 1);

        List<SprintBurndownResponse.BurndownPoint> points = new ArrayList<>();
        double currentIdeal = totalSp;

        LocalDate cur = start;
        while (!cur.isAfter(end)) {
            points.add(SprintBurndownResponse.BurndownPoint.builder()
                    .date(cur)
                    .idealRemaining(Math.max(0.0, currentIdeal))
                    .actualRemaining((double) totalSp) // Simplified estimation
                    .completedToDate(0.0)
                    .build());
            currentIdeal -= dailyIdealDrop;
            cur = cur.plusDays(1);
        }

        return SprintBurndownResponse.builder()
                .sprintId(sprintId)
                .sprintName(sprint.getName())
                .totalPlannedStoryPoints(totalSp)
                .burndownData(points)
                .build();
    }

    private Sprint findSprintOrThrow(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with ID: " + id));
        if (sprint.isDeleted()) {
            throw new ResourceNotFoundException("Sprint not found with ID: " + id);
        }
        return sprint;
    }

    private SprintResponse mapToResponse(Sprint s) {
        return SprintResponse.builder()
                .id(s.getId())
                .projectId(s.getProjectId())
                .name(s.getName())
                .goal(s.getGoal())
                .status(s.getStatus())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .completedAt(s.getCompletedAt())
                .plannedStoryPoints(s.getPlannedStoryPoints())
                .completedStoryPoints(s.getCompletedStoryPoints())
                .velocity(s.getVelocity())
                .capacity(s.getCapacity())
                .completedTaskCount(s.getCompletedTaskCount())
                .totalTaskCount(s.getTotalTaskCount())
                .orderIndex(s.getOrderIndex())
                .archivedAt(s.getArchivedAt())
                .cancelledAt(s.getCancelledAt())
                .createdAt(s.getCreatedAt())
                .updatedAt(s.getUpdatedAt())
                .build();
    }
}
