package org.SprintForge.modules.workspace.workspace.calendar.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workspace.workspace.calendar.dto.request.CreateHolidayRequest;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.BusinessCalendarResponse;
import org.SprintForge.modules.workspace.workspace.calendar.dto.response.HolidayResponse;
import org.SprintForge.modules.workspace.workspace.calendar.entity.BusinessHoliday;
import org.SprintForge.modules.workspace.workspace.calendar.event.BusinessCalendarUpdatedEvent;
import org.SprintForge.modules.workspace.workspace.calendar.mapper.BusinessCalendarMapper;
import org.SprintForge.modules.workspace.workspace.calendar.repository.BusinessHolidayRepository;
import org.SprintForge.modules.workspace.workspace.entity.WorkspacePreference;
import org.SprintForge.modules.workspace.workspace.repository.WorkspacePreferenceRepository;
import org.SprintForge.modules.workspace.workspace.validation.BusinessCalendarValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessCalendarServiceImpl implements BusinessCalendarService {

    private final BusinessHolidayRepository businessHolidayRepository;
    private final WorkspacePreferenceRepository workspacePreferenceRepository;
    private final BusinessCalendarMapper businessCalendarMapper;
    private final BusinessCalendarValidator businessCalendarValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional(readOnly = true)
    public BusinessCalendarResponse getCalendar(Long workspaceId, int year) {
        WorkspacePreference prefs = getWorkspacePrefs(workspaceId);
        List<HolidayResponse> holidays = getHolidays(workspaceId, year);
        
        List<String> workingDays = prefs.getWorkingDays() != null && !prefs.getWorkingDays().isEmpty() 
            ? Arrays.asList(prefs.getWorkingDays().split(",")) 
            : List.of();

        return BusinessCalendarResponse.builder()
                .workspaceId(workspaceId)
                .timezone(prefs.getTimezone())
                .workingDays(workingDays)
                .businessHoursStart(prefs.getBusinessHoursStart())
                .businessHoursEnd(prefs.getBusinessHoursEnd())
                .weekStartsOn(prefs.getWeekStartsOn())
                .holidays(holidays)
                .year(year)
                .build();
    }

    @Override
    @Transactional
    public HolidayResponse addHoliday(Long workspaceId, CreateHolidayRequest request, Long actorId) {
        businessCalendarValidator.validateDate(request.getDate());
        businessCalendarValidator.validateNoConflict(workspaceId, request.getDate());

        BusinessHoliday holiday = businessCalendarMapper.toEntity(request);
        holiday.setWorkspaceId(workspaceId);
        if (actorId != null) {
            holiday.setCreatedBy(actorId.toString());
            holiday.setUpdatedBy(actorId.toString());
        }

        BusinessHoliday saved = businessHolidayRepository.save(holiday);
        eventPublisher.publishEvent(new BusinessCalendarUpdatedEvent(workspaceId, "ADD_HOLIDAY", actorId, LocalDateTime.now()));
        return businessCalendarMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void removeHoliday(Long workspaceId, Long holidayId, Long actorId) {
        BusinessHoliday holiday = businessHolidayRepository.findById(holidayId)
                .orElseThrow(() -> new ResourceNotFoundException("Holiday not found with id: " + holidayId));
        
        if (!holiday.getWorkspaceId().equals(workspaceId)) {
            throw new ResourceNotFoundException("Holiday not found in this workspace");
        }

        String actor = actorId != null ? actorId.toString() : "system";
        holiday.setDeleted(true);
        holiday.setDeletedAt(LocalDateTime.now());
        holiday.setDeletedBy(actor);
        businessHolidayRepository.save(holiday);
        
        eventPublisher.publishEvent(new BusinessCalendarUpdatedEvent(workspaceId, "REMOVE_HOLIDAY", actorId, LocalDateTime.now()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayResponse> getHolidays(Long workspaceId, int year) {
        return businessHolidayRepository.findByWorkspaceIdAndYear(workspaceId, year).stream()
                .map(businessCalendarMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isWorkingDay(Long workspaceId, LocalDate date) {
        WorkspacePreference prefs = getWorkspacePrefs(workspaceId);
        String dayOfWeek = date.getDayOfWeek().name().substring(0, 3); // MON, TUE, etc.
        
        if (prefs.getWorkingDays() == null || !prefs.getWorkingDays().contains(dayOfWeek)) {
            return false;
        }

        return !businessHolidayRepository.existsByWorkspaceIdAndDateAndIsDeletedFalse(workspaceId, date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalDate> getWorkingDays(Long workspaceId, LocalDate from, LocalDate to) {
        List<LocalDate> workingDays = new ArrayList<>();
        LocalDate current = from;
        while (!current.isAfter(to)) {
            if (isWorkingDay(workspaceId, current)) {
                workingDays.add(current);
            }
            current = current.plusDays(1);
        }
        return workingDays;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getBusinessHours(Long workspaceId) {
        WorkspacePreference prefs = getWorkspacePrefs(workspaceId);
        return Map.of(
            "start", prefs.getBusinessHoursStart() != null ? prefs.getBusinessHoursStart() : "09:00",
            "end", prefs.getBusinessHoursEnd() != null ? prefs.getBusinessHoursEnd() : "17:00"
        );
    }

    private WorkspacePreference getWorkspacePrefs(Long workspaceId) {
        return workspacePreferenceRepository.findByWorkspaceIdAndUserIdIsNull(workspaceId)
                .orElseGet(() -> WorkspacePreference.builder().workspaceId(workspaceId).build());
    }
}
