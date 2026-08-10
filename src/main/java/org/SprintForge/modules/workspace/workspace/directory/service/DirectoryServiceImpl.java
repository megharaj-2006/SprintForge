package org.SprintForge.modules.workspace.workspace.directory.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.workspace.directory.dto.response.DirectoryMemberResponse;
import org.SprintForge.modules.workspace.workspace.directory.dto.response.DirectoryResponse;
import org.SprintForge.modules.workspace.workspace.entity.WorkspaceMember;
import org.SprintForge.modules.workspace.workspace.repository.WorkspaceMemberRepository;
import org.SprintForge.modules.workspace.workspace.team.repository.TeamMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional(readOnly = true)
    public DirectoryResponse getDirectory(Long workspaceId, int page, int size) {
        Page<WorkspaceMember> memberPage = workspaceMemberRepository
                .searchWorkspaceMembers(workspaceId, null, PageRequest.of(page, size));

        List<DirectoryMemberResponse> members = memberPage.getContent().stream()
                .map(this::mapToDirectoryMember)
                .collect(Collectors.toList());

        return DirectoryResponse.builder()
                .workspaceId(workspaceId)
                .totalMembers(memberPage.getTotalElements())
                .members(members)
                .page(page)
                .size(size)
                .totalPages(memberPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public DirectoryResponse searchDirectory(Long workspaceId, String query, int page, int size) {
        Page<WorkspaceMember> memberPage = workspaceMemberRepository
                .searchWorkspaceMembers(workspaceId, query, PageRequest.of(page, size));

        List<DirectoryMemberResponse> members = memberPage.getContent().stream()
                .map(this::mapToDirectoryMember)
                .collect(Collectors.toList());

        return DirectoryResponse.builder()
                .workspaceId(workspaceId)
                .totalMembers(memberPage.getTotalElements())
                .members(members)
                .page(page)
                .size(size)
                .totalPages(memberPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DirectoryMemberResponse> getTeamDirectory(Long workspaceId, Long teamId) {
        return teamMemberRepository.findByTeamIdAndIsDeletedFalse(teamId).stream()
                .map(tm -> workspaceMemberRepository.findById(tm.getMemberId()).orElse(null))
                .filter(m -> m != null && !m.isDeleted())
                .map(this::mapToDirectoryMember)
                .collect(Collectors.toList());
    }

    private DirectoryMemberResponse mapToDirectoryMember(WorkspaceMember member) {
        return DirectoryMemberResponse.builder()
                .userId(member.getUserId())
                .roleId(member.getRoleId())
                .teamIds(Collections.emptyList())
                .teamNames(Collections.emptyList())
                .joinedAt(member.getJoinedAt())
                .build();
    }
}
