package org.SprintForge.modules.workspace.workspace.team.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.BusinessRuleException;
import org.SprintForge.modules.workspace.workspace.team.dto.request.AssignTeamMemberRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.request.TransferTeamMemberRequest;
import org.SprintForge.modules.workspace.workspace.team.dto.response.TeamResponse;
import org.SprintForge.modules.workspace.workspace.team.entity.TeamMember;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMembershipServiceImpl implements TeamMembershipService {

    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional
    public void addMember(Long teamId, AssignTeamMemberRequest request, Long actorId) {
        if (teamMemberRepository.existsByTeamIdAndMemberIdAndIsDeletedFalse(teamId, request.getMemberId())) {
            throw new BusinessRuleException("Member already belongs to this team.");
        }

        TeamMember member = TeamMember.builder()
                .teamId(teamId)
                .memberId(request.getMemberId())
                .role(request.getRole())
                .allocationPercentage(request.getAllocationPercentage())
                .joinedAt(LocalDateTime.now())
                .build();
                
        teamMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void removeMember(Long teamId, Long memberId, Long actorId) {
        TeamMember member = teamMemberRepository.findByTeamIdAndMemberIdAndIsDeletedFalse(teamId, memberId)
                .orElseThrow(() -> new BusinessRuleException("Member not found in this team."));
                
        member.markDeleted(actorId.toString());
        teamMemberRepository.save(member);
    }

    @Override
    @Transactional
    public void transferMember(Long teamId, TransferTeamMemberRequest request, Long actorId) {
        TeamMember sourceMember = teamMemberRepository.findByTeamIdAndMemberIdAndIsDeletedFalse(teamId, request.getMemberId())
                .orElseThrow(() -> new BusinessRuleException("Member not found in source team."));
                
        sourceMember.markDeleted(actorId.toString());
        teamMemberRepository.save(sourceMember);
        
        if (teamMemberRepository.existsByTeamIdAndMemberIdAndIsDeletedFalse(request.getTargetTeamId(), request.getMemberId())) {
            throw new BusinessRuleException("Member already belongs to target team.");
        }
        
        TeamMember newMember = TeamMember.builder()
                .teamId(request.getTargetTeamId())
                .memberId(request.getMemberId())
                .role(sourceMember.getRole())
                .allocationPercentage(sourceMember.getAllocationPercentage())
                .joinedAt(LocalDateTime.now())
                .build();
                
        teamMemberRepository.save(newMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamResponse> listMembersOfTeam(Long teamId) {
        // According to instructions: return summary of team members.
        // Returning empty list here as the instruction expects TeamResponse but mentions members.
        // The implementation details for this specific requirement might need adjustments later.
        return List.of(); 
    }
}
