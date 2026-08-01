package org.SprintForge.modules.workspace.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.calendar.dto.response.CalendarViewResponse;
import org.SprintForge.modules.workspace.calendar.service.CalendarIntegrationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
@Validated
@Tag(name = "Calendar Controller", description = "REST endpoints for calendar views (Day, Week, Month, Agenda)")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CalendarIntegrationController {

    private final CalendarIntegrationService calendarService;

    @Operation(summary = "Get calendar events by date range")
    @GetMapping
    public ResponseEntity<CalendarViewResponse> getCalendar(
            @RequestParam(value = "mode", defaultValue = "MONTH") String mode,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(calendarService.getCalendarView(mode, startDate, endDate));
    }

    @Operation(summary = "Get day calendar view")
    @GetMapping("/day")
    public ResponseEntity<CalendarViewResponse> getDayCalendar(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate target = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(calendarService.getCalendarView("DAY", target, target));
    }

    @Operation(summary = "Get week calendar view")
    @GetMapping("/week")
    public ResponseEntity<CalendarViewResponse> getWeekCalendar(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate start = date != null ? date : LocalDate.now();
        return ResponseEntity.ok(calendarService.getCalendarView("WEEK", start, start.plusDays(7)));
    }

    @Operation(summary = "Get month calendar view")
    @GetMapping("/month")
    public ResponseEntity<CalendarViewResponse> getMonthCalendar(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDate start = date != null ? date.withDayOfMonth(1) : LocalDate.now().withDayOfMonth(1);
        return ResponseEntity.ok(calendarService.getCalendarView("MONTH", start, start.plusMonths(1).minusDays(1)));
    }
}
