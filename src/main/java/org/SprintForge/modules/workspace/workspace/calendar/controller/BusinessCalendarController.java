package org.SprintForge.modules.workspace.workspace.calendar.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.calendar.dto.request.CreateHolidayRequest;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.BusinessCalendarResponse;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.HolidayResponse;
import org.SprintForge.modules.workspace.workspace.calendar.service.BusinessCalendarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workspaces/{workspaceId}/calendar")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Tag(name = "Business Calendar")
public class BusinessCalendarController {

    private final BusinessCalendarService businessCalendarService;

    @GetMapping
    public ResponseEntity<BusinessCalendarResponse> getCalendar(
            @PathVariable Long workspaceId,
            @RequestParam int year) {
        return ResponseEntity.ok(businessCalendarService.getCalendar(workspaceId, year));
    }

    @GetMapping("/holidays")
    public ResponseEntity<List<HolidayResponse>> getHolidays(
            @PathVariable Long workspaceId,
            @RequestParam int year) {
        return ResponseEntity.ok(businessCalendarService.getHolidays(workspaceId, year));
    }

    @PostMapping("/holidays")
    public ResponseEntity<HolidayResponse> addHoliday(
            @PathVariable Long workspaceId,
            @Valid @RequestBody CreateHolidayRequest request,
            @RequestHeader(value = "X-User-Id", required = false) Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(businessCalendarService.addHoliday(workspaceId, request, actorId));
    }

    @DeleteMapping("/holidays/{holidayId}")
    public ResponseEntity<Void> removeHoliday(
            @PathVariable Long workspaceId,
            @PathVariable Long holidayId,
            @RequestHeader(value = "X-User-Id", required = false) Long actorId) {
        businessCalendarService.removeHoliday(workspaceId, holidayId, actorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/working-days")
    public ResponseEntity<List<LocalDate>> getWorkingDays(
            @PathVariable Long workspaceId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(businessCalendarService.getWorkingDays(workspaceId, from, to));
    }
}
