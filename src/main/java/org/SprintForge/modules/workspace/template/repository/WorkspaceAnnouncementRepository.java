package org.SprintForge.modules.workspace.template.repository;

import org.SprintForge.modules.workspace.template.entity.WorkspaceAnnouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceAnnouncementRepository extends JpaRepository<WorkspaceAnnouncement, Long>, JpaSpecificationExecutor<WorkspaceAnnouncement> {
}