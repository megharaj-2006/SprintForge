package org.SprintForge.modules.workspace.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BadRequestException;
import org.SprintForge.common.exception.InvalidOperationException;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.task.dto.request.*;
import org.SprintForge.modules.workspace.task.dto.response.OccurrencePreviewResponse;
import org.SprintForge.modules.workspace.task.dto.response.RecurringTaskResponse;
import org.SprintForge.modules.workspace.task.dto.response.TaskResponse;
import org.SprintForge.modules.workspace.task.entity.RecurringTask;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.entity.enums.RecurringTaskFrequency;
import org.SprintForge.modules.workspace.task.event.*;
import org.SprintForge.modules.workspace.task.repository.RecurringTaskRepository;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecurringTaskServiceImpl implements RecurringTaskService {

    private final RecurringTaskRepository recurringTaskRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public RecurringTaskResponse scheduleRecurringTask(Long parentTaskId, CreateRecurringTaskRequest request, Long actorId) {
        log.info("Scheduling recurring task for parent task {} with frequency {}", parentTaskId, request.getFrequency());

        Task parentTask = taskRepository.findById(parentTaskId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent task not found with ID: " + parentTaskId));

        if (parentTask.isDeleted() || (parentTask.getArchived() != null && parentTask.getArchived())) {
            throw new InvalidOperationException("Archived or deleted task cannot be scheduled for recurrence");
        }

        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Start date cannot be in the past");
        }
        if (request.getEndDate() != null && request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }

        RecurringTask recurringTask = new RecurringTask();
        recurringTask.setTaskId(parentTaskId);
        recurringTask.setWorkspaceId(parentTask.getProject() != null ? parentTask.getProject().getWorkspaceId() : null);
        recurringTask.setProjectId(parentTask.getProject() != null ? parentTask.getProject().getId() : null);
        recurringTask.setFrequency(request.getFrequency());
        recurringTask.setIntervalValue(request.getIntervalValue() != null ? Math.max(1, request.getIntervalValue()) : 1);
        
        if (request.getDaysOfWeek() != null && !request.getDaysOfWeek().isEmpty()) {
            recurringTask.setDaysOfWeek(String.join(",", request.getDaysOfWeek()));
        }
        
        recurringTask.setDayOfMonth(request.getDayOfMonth());
        recurringTask.setMonthOfYear(request.getMonthOfYear());
        recurringTask.setCronExpression(request.getCronExpression());
        recurringTask.setStartDate(request.getStartDate());
        recurringTask.setEndDate(request.getEndDate());
        recurringTask.setMaxOccurrences(request.getMaxOccurrences());
        recurringTask.setTimezone(request.getTimezone() != null ? request.getTimezone() : "UTC");
        recurringTask.setSkipWeekends(request.getSkipWeekends() != null ? request.getSkipWeekends() : false);
        recurringTask.setSkipHolidays(request.getSkipHolidays() != null ? request.getSkipHolidays() : false);
        recurringTask.setAutoAssign(request.getAutoAssign() != null ? request.getAutoAssign() : true);
        recurringTask.setAutoNotify(request.getAutoNotify() != null ? request.getAutoNotify() : true);
        recurringTask.setCreatedByUserId(actorId);
        recurringTask.setEnabled(true);
        recurringTask.setPaused(false);
        recurringTask.setGeneratedOccurrences(0);

        LocalDateTime firstExecution = calculateNextRun(recurringTask, request.getStartDate().atStartOfDay());
        recurringTask.setNextExecution(firstExecution);

        RecurringTask saved = recurringTaskRepository.save(recurringTask);
        eventPublisher.publishEvent(new RecurringTaskScheduledEvent(saved.getId(), parentTaskId, actorId));

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public RecurringTaskResponse updateRecurringTask(Long id, UpdateRecurringTaskRequest request, Long actorId) {
        RecurringTask recurringTask = findRecurringTaskOrThrow(id);

        if (request.getFrequency() != null) recurringTask.setFrequency(request.getFrequency());
        if (request.getIntervalValue() != null) recurringTask.setIntervalValue(Math.max(1, request.getIntervalValue()));
        if (request.getDaysOfWeek() != null) recurringTask.setDaysOfWeek(String.join(",", request.getDaysOfWeek()));
        if (request.getDayOfMonth() != null) recurringTask.setDayOfMonth(request.getDayOfMonth());
        if (request.getMonthOfYear() != null) recurringTask.setMonthOfYear(request.getMonthOfYear());
        if (request.getCronExpression() != null) recurringTask.setCronExpression(request.getCronExpression());
        if (request.getStartDate() != null) recurringTask.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) recurringTask.setEndDate(request.getEndDate());
        if (request.getMaxOccurrences() != null) recurringTask.setMaxOccurrences(request.getMaxOccurrences());
        if (request.getTimezone() != null) recurringTask.setTimezone(request.getTimezone());
        if (request.getSkipWeekends() != null) recurringTask.setSkipWeekends(request.getSkipWeekends());
        if (request.getSkipHolidays() != null) recurringTask.setSkipHolidays(request.getSkipHolidays());
        if (request.getAutoAssign() != null) recurringTask.setAutoAssign(request.getAutoAssign());
        if (request.getAutoNotify() != null) recurringTask.setAutoNotify(request.getAutoNotify());

        LocalDateTime nextRun = calculateNextRun(recurringTask, LocalDateTime.now());
        recurringTask.setNextExecution(nextRun);

        RecurringTask updated = recurringTaskRepository.save(recurringTask);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void cancelRecurringTask(Long id, Long actorId) {
        RecurringTask recurringTask = findRecurringTaskOrThrow(id);
        recurringTask.setDeleted(true);
        recurringTask.setEnabled(false);
        recurringTaskRepository.save(recurringTask);

        eventPublisher.publishEvent(new RecurringTaskCancelledEvent(id, actorId));
    }

    @Override
    @Transactional
    public RecurringTaskResponse pauseRecurringTask(Long id, PauseRecurringTaskRequest request, Long actorId) {
        RecurringTask recurringTask = findRecurringTaskOrThrow(id);
        recurringTask.setPaused(true);
        RecurringTask saved = recurringTaskRepository.save(recurringTask);

        eventPublisher.publishEvent(new RecurringTaskPausedEvent(id, actorId));
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public RecurringTaskResponse resumeRecurringTask(Long id, Long actorId) {
        RecurringTask recurringTask = findRecurringTaskOrThrow(id);
        recurringTask.setPaused(false);
        
        LocalDateTime nextRun = calculateNextRun(recurringTask, LocalDateTime.now());
        recurringTask.setNextExecution(nextRun);

        RecurringTask saved = recurringTaskRepository.save(recurringTask);
        eventPublisher.publishEvent(new RecurringTaskResumedEvent(id, actorId));

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OccurrencePreviewResponse previewOccurrences(Long id, PreviewOccurrencesRequest request) {
        RecurringTask tempTask;
        if (id != null) {
            tempTask = findRecurringTaskOrThrow(id);
        } else {
            tempTask = new RecurringTask();
            tempTask.setFrequency(request.getFrequency());
            tempTask.setIntervalValue(request.getIntervalValue() != null ? request.getIntervalValue() : 1);
            if (request.getDaysOfWeek() != null) tempTask.setDaysOfWeek(String.join(",", request.getDaysOfWeek()));
            tempTask.setDayOfMonth(request.getDayOfMonth());
            tempTask.setMonthOfYear(request.getMonthOfYear());
            tempTask.setStartDate(request.getStartDate() != null ? request.getStartDate() : LocalDate.now());
            tempTask.setEndDate(request.getEndDate());
        }

        int count = request.getCount() != null ? request.getCount() : 10;
        List<LocalDateTime> dates = new ArrayList<>();
        LocalDateTime current = tempTask.getStartDate() != null ? tempTask.getStartDate().atStartOfDay() : LocalDateTime.now();

        for (int i = 0; i < count; i++) {
            current = calculateNextRun(tempTask, current);
            if (current == null) break;
            dates.add(current);
            current = current.plusDays(1);
        }

        return OccurrencePreviewResponse.builder()
                .recurringTaskId(id)
                .requestedCount(count)
                .previewDates(dates)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public RecurringTaskResponse getRecurringTaskByTaskId(Long taskId) {
        RecurringTask recurringTask = recurringTaskRepository.findByTaskIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring schedule not found for task ID: " + taskId));
        return mapToResponse(recurringTask);
    }

    @Override
    @Transactional(readOnly = true)
    public RecurringTaskResponse getRecurringTaskById(Long id) {
        RecurringTask recurringTask = findRecurringTaskOrThrow(id);
        return mapToResponse(recurringTask);
    }

    @Override
    @Transactional
    public void executeDueRecurringTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<RecurringTask> dueTasks = recurringTaskRepository.findDueRecurringTasks(now);

        for (RecurringTask recurring : dueTasks) {
            try {
                if (recurring.getMaxOccurrences() != null && recurring.getGeneratedOccurrences() >= recurring.getMaxOccurrences()) {
                    recurring.setEnabled(false);
                    recurringTaskRepository.save(recurring);
                    continue;
                }
                if (recurring.getEndDate() != null && now.toLocalDate().isAfter(recurring.getEndDate())) {
                    recurring.setEnabled(false);
                    recurringTaskRepository.save(recurring);
                    continue;
                }

                Task parentTask = taskRepository.findById(recurring.getTaskId()).orElse(null);
                if (parentTask == null || parentTask.isDeleted() || (parentTask.getArchived() != null && parentTask.getArchived())) {
                    recurring.setEnabled(false);
                    recurringTaskRepository.save(recurring);
                    continue;
                }

                Long projectId = parentTask.getProject() != null ? parentTask.getProject().getId() : null;
                Long sprintId = parentTask.getSprint() != null ? parentTask.getSprint().getId() : null;

                CreateTaskRequest cloneReq = CreateTaskRequest.builder()
                        .projectId(projectId)
                        .title(parentTask.getTitle() + " (Recurring)")
                        .description(parentTask.getDescription())
                        .estimatedHours(parentTask.getEstimatedHours())
                        .storyPoints(parentTask.getStoryPoints())
                        .dueDate(now)
                        .sprintId(sprintId)
                        .build();

                TaskResponse generated = taskService.createTask(cloneReq, recurring.getCreatedByUserId());

                recurring.setGeneratedOccurrences(recurring.getGeneratedOccurrences() + 1);
                recurring.setLastExecution(now);

                LocalDateTime nextRun = calculateNextRun(recurring, now.plusMinutes(1));
                if (nextRun == null || (recurring.getMaxOccurrences() != null && recurring.getGeneratedOccurrences() >= recurring.getMaxOccurrences())) {
                    recurring.setEnabled(false);
                    recurring.setNextExecution(null);
                } else {
                    recurring.setNextExecution(nextRun);
                }

                recurringTaskRepository.save(recurring);

                eventPublisher.publishEvent(new RecurringTaskGeneratedEvent(recurring.getId(), recurring.getTaskId(), generated.getId()));

            } catch (Exception e) {
                log.error("Error executing recurring task {}", recurring.getId(), e);
            }
        }
    }

    @Override
    public LocalDateTime calculateNextRun(RecurringTask recurringTask, LocalDateTime fromTime) {
        if (recurringTask == null || !recurringTask.getEnabled() || (recurringTask.getPaused() != null && recurringTask.getPaused())) {
            return null;
        }

        LocalDateTime cursor = fromTime;
        int interval = recurringTask.getIntervalValue() != null ? recurringTask.getIntervalValue() : 1;

        switch (recurringTask.getFrequency()) {
            case DAILY:
            case EVERY_X_DAYS:
                cursor = cursor.plusDays(interval);
                break;
            case WEEKLY:
            case EVERY_X_WEEKS:
                cursor = cursor.plusWeeks(interval);
                break;
            case SPECIFIC_WEEKDAYS:
                cursor = getNextSpecificWeekday(cursor, recurringTask.getDaysOfWeek());
                break;
            case MONTHLY:
            case EVERY_X_MONTHS:
                cursor = cursor.plusMonths(interval);
                if (recurringTask.getDayOfMonth() != null) {
                    int maxDays = cursor.toLocalDate().lengthOfMonth();
                    cursor = cursor.withDayOfMonth(Math.min(recurringTask.getDayOfMonth(), maxDays));
                }
                break;
            case YEARLY:
                cursor = cursor.plusYears(interval);
                break;
            default:
                cursor = cursor.plusDays(1);
        }

        if (recurringTask.getSkipWeekends() != null && recurringTask.getSkipWeekends()) {
            while (cursor.getDayOfWeek() == DayOfWeek.SATURDAY || cursor.getDayOfWeek() == DayOfWeek.SUNDAY) {
                cursor = cursor.plusDays(1);
            }
        }

        return cursor;
    }

    private LocalDateTime getNextSpecificWeekday(LocalDateTime from, String daysOfWeekStr) {
        if (daysOfWeekStr == null || daysOfWeekStr.isBlank()) {
            return from.plusDays(1);
        }
        List<DayOfWeek> days = Arrays.stream(daysOfWeekStr.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());

        LocalDateTime next = from.plusDays(1);
        while (!days.contains(next.getDayOfWeek())) {
            next = next.plusDays(1);
        }
        return next;
    }

    private RecurringTask findRecurringTaskOrThrow(Long id) {
        RecurringTask recurringTask = recurringTaskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurring task schedule not found with ID: " + id));
        if (recurringTask.isDeleted()) {
            throw new ResourceNotFoundException("Recurring task schedule not found with ID: " + id);
        }
        return recurringTask;
    }

    private RecurringTaskResponse mapToResponse(RecurringTask task) {
        List<String> daysOfWeekList = task.getDaysOfWeek() != null ? Arrays.asList(task.getDaysOfWeek().split(",")) : new ArrayList<>();

        return RecurringTaskResponse.builder()
                .id(task.getId())
                .taskId(task.getTaskId())
                .workspaceId(task.getWorkspaceId())
                .projectId(task.getProjectId())
                .frequency(task.getFrequency())
                .intervalValue(task.getIntervalValue())
                .daysOfWeek(daysOfWeekList)
                .dayOfMonth(task.getDayOfMonth())
                .monthOfYear(task.getMonthOfYear())
                .cronExpression(task.getCronExpression())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .maxOccurrences(task.getMaxOccurrences())
                .generatedOccurrences(task.getGeneratedOccurrences())
                .nextExecution(task.getNextExecution())
                .lastExecution(task.getLastExecution())
                .timezone(task.getTimezone())
                .paused(task.getPaused())
                .enabled(task.getEnabled())
                .skipWeekends(task.getSkipWeekends())
                .skipHolidays(task.getSkipHolidays())
                .autoAssign(task.getAutoAssign())
                .autoNotify(task.getAutoNotify())
                .createdByUserId(task.getCreatedByUserId())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
