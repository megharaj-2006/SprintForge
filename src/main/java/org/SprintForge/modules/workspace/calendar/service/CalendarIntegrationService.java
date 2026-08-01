package org.SprintForge.modules.workspace.calendar.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.calendar.dto.response.CalendarEventResponse;
import org.SprintForge.modules.workspace.calendar.dto.response.CalendarViewResponse;
import org.SprintForge.modules.workspace.task.entity.Task;
import org.SprintForge.modules.workspace.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarIntegrationService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public CalendarViewResponse getCalendarView(String viewMode, LocalDate startDate, LocalDate endDate) {
        LocalDate start = startDate != null ? startDate : LocalDate.now().withDayOfMonth(1);
        LocalDate end = endDate != null ? endDate : start.plusMonths(1).minusDays(1);

        LocalDateTime startDt = start.atStartOfDay();
        LocalDateTime endDt = end.atTime(23, 59, 59);

        List<Task> tasks = taskRepository.findAll().stream()
                .filter(t -> !t.isDeleted() && t.getDueDate() != null && !t.getDueDate().isBefore(startDt) && !t.getDueDate().isAfter(endDt))
                .collect(Collectors.toList());

        List<CalendarEventResponse> events = new ArrayList<>();
        for (Task t : tasks) {
            events.add(CalendarEventResponse.builder()
                    .id("TASK_" + t.getId())
                    .title(t.getTitle())
                    .eventType("TASK_DUE")
                    .startTime(t.getDueDate())
                    .endTime(t.getDueDate())
                    .status(t.getStatus().name())
                    .color("#3B82F6")
                    .entityUrl("/api/v1/tasks/" + t.getId())
                    .build());
        }

        return CalendarViewResponse.builder()
                .viewMode(viewMode != null ? viewMode.toUpperCase() : "MONTH")
                .startDate(start)
                .endDate(end)
                .totalEvents(events.size())
                .events(events)
                .build();
    }
}
