package org.SprintForge.modules.workspace.workspace.calendar.service;

import org.SprintForge.modules.workspace.workspace.calendar.dto.request.CreateHolidayRequest;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.BusinessCalendarResponse;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.HolidayResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BusinessCalendarService {
    BusinessCalendarResponse getCalendar(Long workspaceId, int year);
    HolidayResponse addHoliday(Long workspaceId, CreateHolidayRequest request, Long actorId);
    void removeHoliday(Long workspaceId, Long holidayId, Long actorId);
    List<HolidayResponse> getHolidays(Long workspaceId, int year);
    boolean isWorkingDay(Long workspaceId, LocalDate date);
    List<LocalDate> getWorkingDays(Long workspaceId, LocalDate from, LocalDate to);
    Map<String, String> getBusinessHours(Long workspaceId);
}
