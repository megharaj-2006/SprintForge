package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectCalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCalendarEventRepository extends JpaRepository<ProjectCalendarEvent, Long>, JpaSpecificationExecutor<ProjectCalendarEvent> {
}