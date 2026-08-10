package org.SprintForge.modules.workspace.workspace.directory.service;

import org.SprintForge.modules.workspace.workspace.directory.dto.response.DirectoryMemberResponse;
import org.SprintForge.modules.workspace.workspace.directory.dto.response.DirectoryResponse;

import java.util.List;

public interface DirectoryService {
    DirectoryResponse getDirectory(Long workspaceId, int page, int size);
    DirectoryResponse searchDirectory(Long workspaceId, String query, int page, int size);
    List<DirectoryMemberResponse> getTeamDirectory(Long workspaceId, Long teamId);
}
