package org.SprintForge.modules.workspace.timelog.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeTrackingServiceImplTest {

    @Mock
    private TimeEntryRepository timeEntryRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TimeEntryMapper timeEntryMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private TimeTrackingService timeTrackingService;

    private Task mockTask;
    private TimeEntry mockTimeEntry;

    @BeforeEach
    void setUp() {
        timeTrackingService = new TimeTrackingServiceImpl(
                timeEntryRepository,
                taskRepository,
                timeEntryMapper,
                eventPublisher
        );

        mockTask = new Task();
        mockTask.setId(10L);
        mockTask.setArchived(false);
        mockTask.setDeleted(false);

        mockTimeEntry = new TimeEntry();
        mockTimeEntry.setId(40L);
        mockTimeEntry.setTaskId(10L);
        mockTimeEntry.setUserId(1L);
        mockTimeEntry.setStartTime(LocalDateTime.now().minusHours(2));
        mockTimeEntry.setEndTime(null);
        mockTimeEntry.setDurationMinutes(null);
        mockTimeEntry.setBillable(true);
        mockTimeEntry.setDeleted(false);
    }

    @Test
    void startTimer_Success() {
        StartTimerRequest request = new StartTimerRequest("Work Description", true);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryRepository.findByUserIdAndEndTimeIsNullAndIsDeletedFalse(1L)).thenReturn(Optional.empty());
        when(timeEntryRepository.save(any(TimeEntry.class))).thenAnswer(inv -> {
            TimeEntry te = inv.getArgument(0);
            te.setId(40L);
            return te;
        });

        TimeEntryResponse expectedResponse = new TimeEntryResponse();
        expectedResponse.setId(40L);
        expectedResponse.setTaskId(10L);
        when(timeEntryMapper.toResponse(any(TimeEntry.class))).thenReturn(expectedResponse);

        TimeEntryResponse response = timeTrackingService.startTimer(10L, request, 1L);

        assertNotNull(response);
        assertEquals(40L, response.getId());
        verify(timeEntryRepository).save(any(TimeEntry.class));
        verify(eventPublisher).publishEvent(any(TimeTrackingStartedEvent.class));
    }

    @Test
    void startTimer_AlreadyRunning() {
        StartTimerRequest request = new StartTimerRequest("Work", true);
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryRepository.findByUserIdAndEndTimeIsNullAndIsDeletedFalse(1L)).thenReturn(Optional.of(mockTimeEntry));

        assertThrows(BusinessRuleException.class, () -> timeTrackingService.startTimer(10L, request, 1L));
        verify(timeEntryRepository, never()).save(any());
    }

    @Test
    void stopTimer_Success() {
        StopTimerRequest request = new StopTimerRequest("Finished Work");
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryRepository.findByUserIdAndEndTimeIsNullAndIsDeletedFalse(1L)).thenReturn(Optional.of(mockTimeEntry));
        when(timeEntryRepository.save(any(TimeEntry.class))).thenReturn(mockTimeEntry);

        TimeEntryResponse expectedResponse = new TimeEntryResponse();
        expectedResponse.setId(40L);
        expectedResponse.setDurationMinutes(120L);
        when(timeEntryMapper.toResponse(any(TimeEntry.class))).thenReturn(expectedResponse);

        TimeEntryResponse response = timeTrackingService.stopTimer(10L, request, 1L);

        assertNotNull(response);
        assertEquals(120L, response.getDurationMinutes());
        assertNotNull(mockTimeEntry.getEndTime());
        assertEquals("Finished Work", mockTimeEntry.getDescription());
        verify(eventPublisher).publishEvent(any(TimeTrackingStoppedEvent.class));
    }

    @Test
    void stopTimer_DifferentTask() {
        StopTimerRequest request = new StopTimerRequest("Finished");
        mockTimeEntry.setTaskId(11L); // running on different task
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryRepository.findByUserIdAndEndTimeIsNullAndIsDeletedFalse(1L)).thenReturn(Optional.of(mockTimeEntry));

        assertThrows(BusinessRuleException.class, () -> timeTrackingService.stopTimer(10L, request, 1L));
    }

    @Test
    void logTime_Success() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now();
        CreateTimeEntryRequest request = new CreateTimeEntryRequest("Log", start, end, null, true);

        TimeEntry mapped = new TimeEntry();
        mapped.setStartTime(start);
        mapped.setEndTime(end);
        mapped.setBillable(true);
        mapped.setDescription("Log");

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryMapper.toEntity(request)).thenReturn(mapped);
        when(timeEntryRepository.save(any(TimeEntry.class))).thenAnswer(inv -> {
            TimeEntry te = inv.getArgument(0);
            te.setId(45L);
            return te;
        });

        TimeEntryResponse expectedResponse = new TimeEntryResponse();
        expectedResponse.setId(45L);
        expectedResponse.setDurationMinutes(60L);
        when(timeEntryMapper.toResponse(any(TimeEntry.class))).thenReturn(expectedResponse);

        TimeEntryResponse response = timeTrackingService.logTime(10L, request, 1L);

        assertNotNull(response);
        assertEquals(60L, response.getDurationMinutes());
        assertEquals(60L, mapped.getDurationMinutes());
        verify(eventPublisher).publishEvent(any(TimeEntryCreatedEvent.class));
    }

    @Test
    void logTime_EndBeforeStart() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().minusHours(1);
        CreateTimeEntryRequest request = new CreateTimeEntryRequest("Log", start, end, null, true);

        TimeEntry mapped = new TimeEntry();
        mapped.setStartTime(start);
        mapped.setEndTime(end);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryMapper.toEntity(request)).thenReturn(mapped);

        assertThrows(BusinessRuleException.class, () -> timeTrackingService.logTime(10L, request, 1L));
    }

    @Test
    void updateTimeEntry_Success() {
        UpdateTimeEntryRequest request = new UpdateTimeEntryRequest("Updated Log", null, null, null, true);
        when(timeEntryRepository.findByIdAndIsDeletedFalse(40L)).thenReturn(Optional.of(mockTimeEntry));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));
        when(timeEntryRepository.save(any(TimeEntry.class))).thenReturn(mockTimeEntry);

        TimeEntryResponse expectedResponse = new TimeEntryResponse();
        expectedResponse.setId(40L);
        expectedResponse.setDescription("Updated Log");
        when(timeEntryMapper.toResponse(any(TimeEntry.class))).thenReturn(expectedResponse);

        TimeEntryResponse response = timeTrackingService.updateTimeEntry(40L, request, 1L);

        assertNotNull(response);
        verify(timeEntryMapper).updateEntity(request, mockTimeEntry);
        verify(eventPublisher).publishEvent(any(TimeEntryUpdatedEvent.class));
    }

    @Test
    void deleteTimeEntry_Success() {
        when(timeEntryRepository.findByIdAndIsDeletedFalse(40L)).thenReturn(Optional.of(mockTimeEntry));
        when(taskRepository.findById(10L)).thenReturn(Optional.of(mockTask));

        timeTrackingService.deleteTimeEntry(40L, 1L);

        assertTrue(mockTimeEntry.isDeleted());
        verify(timeEntryRepository).save(mockTimeEntry);
        verify(eventPublisher).publishEvent(any(TimeEntryDeletedEvent.class));
    }
}
