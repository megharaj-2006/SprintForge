package org.SprintForge.modules.workspace.workspace.repository;

import org.SprintForge.modules.workspace.workspace.entity.WorkspaceInvitation;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceInvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceInvitationRepository extends JpaRepository<WorkspaceInvitation, Long>, JpaSpecificationExecutor<WorkspaceInvitation> {

    Optional<WorkspaceInvitation> findByInviteTokenAndIsDeletedFalse(String inviteToken);

    List<WorkspaceInvitation> findByEmailAndIsDeletedFalse(String email);

    List<WorkspaceInvitation> findByWorkspaceIdAndStatusAndIsDeletedFalse(Long workspaceId, WorkspaceInvitationStatus status);

    List<WorkspaceInvitation> findByEmailAndStatusAndIsDeletedFalse(String email, WorkspaceInvitationStatus status);

    boolean existsByWorkspaceIdAndEmailAndStatusAndIsDeletedFalse(Long workspaceId, String email, WorkspaceInvitationStatus status);

    List<WorkspaceInvitation> findByWorkspaceIdAndIsDeletedFalse(Long workspaceId);

    @Modifying
    @Query("UPDATE WorkspaceInvitation i SET i.isDeleted = true, i.deletedAt = :now, i.status = org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceInvitationStatus.EXPIRED WHERE i.expiresAt < :now AND i.status = org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceInvitationStatus.PENDING AND i.isDeleted = false")
    int deleteExpiredInvitations(@Param("now") LocalDateTime now);
}