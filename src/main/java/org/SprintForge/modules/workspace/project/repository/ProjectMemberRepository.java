package org.SprintForge.modules.workspace.project.repository;

import org.SprintForge.modules.workspace.project.entity.ProjectMember;
import org.SprintForge.modules.workspace.project.entity.enums.ProjectMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>, JpaSpecificationExecutor<ProjectMember> {

    List<ProjectMember> findByProjectIdAndIsDeletedFalse(Long projectId);

    Page<ProjectMember> findByProjectIdAndIsDeletedFalse(Long projectId, Pageable pageable);

    List<ProjectMember> findByWorkspaceMemberIdAndIsDeletedFalse(Long workspaceMemberId);

    Optional<ProjectMember> findByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(Long projectId, Long workspaceMemberId);

    Optional<ProjectMember> findByProjectIdAndUserIdAndIsDeletedFalse(Long projectId, Long userId);

    boolean existsByProjectIdAndWorkspaceMemberIdAndIsDeletedFalse(Long projectId, Long workspaceMemberId);

    boolean existsByProjectIdAndUserIdAndIsDeletedFalse(Long projectId, Long userId);

    List<ProjectMember> findByUserIdAndIsDeletedFalse(Long userId);

    List<ProjectMember> findByProjectIdAndFavoriteTrueAndIsDeletedFalse(Long projectId);

    long countByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ProjectMemberStatus status);

    List<ProjectMember> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ProjectMemberStatus status);
}