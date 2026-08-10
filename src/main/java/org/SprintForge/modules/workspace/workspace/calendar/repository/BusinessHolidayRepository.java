package org.SprintForge.modules.workspace.workspace.calendar.repository;

import org.SprintForge.modules.workspace.workspace.calendar.entity.BusinessHoliday;
import org.SprintForge.modules.workspace.workspace.calendar.entity.enums.HolidayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessHolidayRepository extends JpaRepository<BusinessHoliday, Long>, JpaSpecificationExecutor<BusinessHoliday> {
    List<BusinessHoliday> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);
    List<BusinessHoliday> findByWorkspaceIdAndIsDeletedFalseOrderByDateAsc(Long workspaceId);
    Optional<BusinessHoliday> findByWorkspaceIdAndDateAndIsDeletedFalse(Long workspaceId, LocalDate date);
    List<BusinessHoliday> findByWorkspaceIdAndTypeAndIsDeletedFalse(Long workspaceId, HolidayType type);
    
    @Query("SELECT h FROM BusinessHoliday h WHERE h.workspaceId = :wid AND h.isDeleted = false AND YEAR(h.date) = :year")
    List<BusinessHoliday> findByWorkspaceIdAndYear(@Param("wid") Long workspaceId, @Param("year") int year);
    
    boolean existsByWorkspaceIdAndDateAndIsDeletedFalse(Long workspaceId, LocalDate date);
}
