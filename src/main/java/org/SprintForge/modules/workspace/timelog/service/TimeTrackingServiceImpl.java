package org.SprintForge.modules.workspace.timelog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.SprintForge.modules.workspace.timelog.dto.request.*;
import org.SprintForge.modules.workspace.timelog.dto.response.*;
import org.SprintForge.modules.workspace.timelog.entity.TimeEntry;
import org.SprintForge.modules.workspace.timelog.event.*;
import org.SprintForge.modules.workspace.timelog.mapper.TimeEntryMapper;
import org.SprintForge.modules.workspace.timelog.repository.TimeEntryRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeTrackingServiceImpl implements TimeTrackingService {

    private final TimeEntryRepository timeEntryRepository;
    private final TaskRepository taskRepository;
    private final TimeEntryMapper timeEntryMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TimeEntryResponse startTimer(Long taskId, StartTimerRequest request, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        // 1. Only one active timer per user.
        timeEntryRepository.findByUserIdAndEndTimeIsNullAndIsDeletedFalse(actorId)
                .ifPresent(existing -> {
                    throw new BusinessRuleException("User already has an active running timer on task " + existing.getTaskId());
                });

        TimeEntry entry = new TimeEntry();
        entry.setTaskId(taskId);
        entry.setUserId(actorId);
        entry.setStartTime(LocalDateTime.now());
        entry.setEndTime(null);
        entry.setDescription(request.getDescription());
        entry.setBillable(request.getBillable() == null || request.getBillable());
        entry.setCreatedBy(actorId.toString());

        TimeEntry saved = timeEntryRepository.save(entry);
        eventPublisher.publishEvent(new TimeTrackingStartedEvent(saved.getId(), taskId, actorId, LocalDateTime.now()));

        return timeEntryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TimeEntryResponse stopTimer(Long taskId, StopTimerRequest request, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        TimeEntry active = timeEntryRepository.findByUserIdAndEndTimeIsNullAndIsDeletedFalse(actorId)
                .orElseThrow(() -> new BusinessRuleException("No active timer found for user."));

        if (!active.getTaskId().equals(taskId)) {
            throw new BusinessRuleException("Active timer is running on a different task ID: " + active.getTaskId());
        }

        active.setEndTime(LocalDateTime.now());
        if (request.getDescription() != null) {
            active.setDescription(request.getDescription());
        }

        // Auto-calculate duration (minimum of 1 minute)
        long minutes = Math.max(1, Duration.between(active.getStartTime(), active.getEndTime()).toMinutes());
        active.setDurationMinutes(minutes);
        active.setUpdatedBy(actorId.toString());

        TimeEntry saved = timeEntryRepository.save(active);
        eventPublisher.publishEvent(new TimeTrackingStoppedEvent(saved.getId(), taskId, actorId, minutes, LocalDateTime.now()));

        return timeEntryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TimeEntryResponse logTime(Long taskId, CreateTimeEntryRequest request, Long actorId) {
        Task task = getTaskOrThrow(taskId);
        validateTaskNotArchived(task);

        TimeEntry entry = timeEntryMapper.toEntity(request);
        entry.setTaskId(taskId);
        entry.setUserId(actorId);
        entry.setCreatedBy(actorId.toString());

        if (entry.getEndTime() != null) {
            if (entry.getEndTime().isBefore(entry.getStartTime())) {
                throw new BusinessRuleException("End time must be after start time.");
            }
            if (entry.getDurationMinutes() == null) {
                long minutes = Math.max(1, Duration.between(entry.getStartTime(), entry.getEndTime()).toMinutes());
                entry.setDurationMinutes(minutes);
            }
        } else if (entry.getDurationMinutes() != null) {
            entry.setEndTime(entry.getStartTime().plusMinutes(entry.getDurationMinutes()));
        } else {
            throw new BusinessRuleException("Either end time or duration must be provided.");
        }

        if (entry.getBillable() == null) {
            entry.setBillable(true);
        }

        TimeEntry saved = timeEntryRepository.save(entry);
        eventPublisher.publishEvent(new TimeEntryCreatedEvent(saved.getId(), taskId, actorId, saved.getDurationMinutes(), LocalDateTime.now()));

        return timeEntryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TimeEntryResponse updateTimeEntry(Long id, UpdateTimeEntryRequest request, Long actorId) {
        TimeEntry entry = getEntryOrThrow(id);
        Task task = getTaskOrThrow(entry.getTaskId());
        validateTaskNotArchived(task);

        timeEntryMapper.updateEntity(request, entry);
        entry.setUpdatedBy(actorId.toString());

        if (entry.getEndTime() != null) {
            if (entry.getEndTime().isBefore(entry.getStartTime())) {
                throw new BusinessRuleException("End time must be after start time.");
            }
            long minutes = Math.max(1, Duration.between(entry.getStartTime(), entry.getEndTime()).toMinutes());
            entry.setDurationMinutes(minutes);
        }

        TimeEntry saved = timeEntryRepository.save(entry);
        eventPublisher.publishEvent(new TimeEntryUpdatedEvent(saved.getId(), entry.getTaskId(), actorId, saved.getDurationMinutes(), LocalDateTime.now()));

        return timeEntryMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void deleteTimeEntry(Long id, Long actorId) {
        TimeEntry entry = getEntryOrThrow(id);
        Task task = getTaskOrThrow(entry.getTaskId());
        validateTaskNotArchived(task);

        entry.markDeleted(actorId.toString());
        timeEntryRepository.save(entry);

        eventPublisher.publishEvent(new TimeEntryDeletedEvent(id, entry.getTaskId(), actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public TaskTimeSummaryResponse getTaskTimeSummary(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);
        List<TimeEntry> entries = timeEntryRepository.findByTaskIdAndIsDeletedFalseOrderByStartTimeDesc(taskId);

        long total = 0;
        long billable = 0;
        for (TimeEntry e : entries) {
            if (e.getDurationMinutes() != null) {
                total += e.getDurationMinutes();
                if (Boolean.TRUE.equals(e.getBillable())) {
                    billable += e.getDurationMinutes();
                }
            }
        }

        return TaskTimeSummaryResponse.builder()
                .taskId(taskId)
                .totalDurationMinutes(total)
                .billableDurationMinutes(billable)
                .timeEntries(timeEntryMapper.toResponseList(entries))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserTimeSummaryResponse getUserTimeSummary(Long userId, Long actorId) {
        List<TimeEntry> entries = timeEntryRepository.findByUserIdAndIsDeletedFalseOrderByStartTimeDesc(userId);

        long total = 0;
        long billable = 0;
        for (TimeEntry e : entries) {
            if (e.getDurationMinutes() != null) {
                total += e.getDurationMinutes();
                if (Boolean.TRUE.equals(e.getBillable())) {
                    billable += e.getDurationMinutes();
                }
            }
        }

        return UserTimeSummaryResponse.builder()
                .userId(userId)
                .totalDurationMinutes(total)
                .billableDurationMinutes(billable)
                .timeEntries(timeEntryMapper.toResponseList(entries))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeEntryResponse> getTaskTimeEntries(Long taskId, Long actorId) {
        getTaskOrThrow(taskId);
        List<TimeEntry> list = timeEntryRepository.findByTaskIdAndIsDeletedFalseOrderByStartTimeDesc(taskId);
        return timeEntryMapper.toResponseList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeEntryResponse> getUserTimeEntries(Long userId, Long actorId) {
        List<TimeEntry> list = timeEntryRepository.findByUserIdAndIsDeletedFalseOrderByStartTimeDesc(userId);
        return timeEntryMapper.toResponseList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Long calculateTotalTime(Long taskId) {
        return timeEntryRepository.getTotalTimeForTask(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long calculateBillableHours(Long taskId) {
        List<TimeEntry> list = timeEntryRepository.findByTaskIdAndIsDeletedFalseOrderByStartTimeDesc(taskId);
        long totalBillable = 0;
        for (TimeEntry e : list) {
            if (Boolean.TRUE.equals(e.getBillable()) && e.getDurationMinutes() != null) {
                totalBillable += e.getDurationMinutes();
            }
        }
        return totalBillable;
    }

    private Task getTaskOrThrow(Long taskId) {
        return taskRepository.findById(taskId)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));
    }

    private TimeEntry getEntryOrThrow(Long id) {
        return timeEntryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time entry not found with ID: " + id));
    }

    private void validateTaskNotArchived(Task task) {
        if (Boolean.TRUE.equals(task.getArchived())) {
            throw new BusinessRuleException("Archived tasks cannot be modified.");
        }
    }
}
