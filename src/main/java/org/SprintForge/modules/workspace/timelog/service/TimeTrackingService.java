package org.SprintForge.modules.workspace.timelog.service;

import org.SprintForge.modules.workspace.timelog.dto.request.*;
import org.SprintForge.modules.workspace.timelog.dto.response.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TimeTrackingService {

    TimeEntryResponse startTimer(Long taskId, StartTimerRequest request, Long actorId);

    TimeEntryResponse stopTimer(Long taskId, StopTimerRequest request, Long actorId);

    TimeEntryResponse logTime(Long taskId, CreateTimeEntryRequest request, Long actorId);

    TimeEntryResponse updateTimeEntry(Long id, UpdateTimeEntryRequest request, Long actorId);

    void deleteTimeEntry(Long id, Long actorId);

    TaskTimeSummaryResponse getTaskTimeSummary(Long taskId, Long actorId);

    UserTimeSummaryResponse getUserTimeSummary(Long userId, Long actorId);

    List<TimeEntryResponse> getTaskTimeEntries(Long taskId, Long actorId);

    List<TimeEntryResponse> getUserTimeEntries(Long userId, Long actorId);

    Long calculateTotalTime(Long taskId);

    Long calculateBillableHours(Long taskId);
}
