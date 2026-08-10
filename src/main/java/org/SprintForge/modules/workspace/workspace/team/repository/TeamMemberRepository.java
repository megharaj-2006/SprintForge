package org.SprintForge.modules.workspace.workspace.team.repository;

import org.SprintForge.modules.workspace.workspace.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>, JpaSpecificationExecutor<TeamMember> {
    List<TeamMember> findByTeamIdAndIsDeletedFalse(Long teamId);
    List<TeamMember> findByMemberIdAndIsDeletedFalse(Long memberId);
    Optional<TeamMember> findByTeamIdAndMemberIdAndIsDeletedFalse(Long teamId, Long memberId);
    boolean existsByTeamIdAndMemberIdAndIsDeletedFalse(Long teamId, Long memberId);
    long countByTeamIdAndIsDeletedFalse(Long teamId);
}
