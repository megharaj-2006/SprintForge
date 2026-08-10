package org.SprintForge.modules.workspace.workspace.directory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryResponse {
    private Long workspaceId;
    private long totalMembers;
    private List<DirectoryMemberResponse> members;
    private int page;
    private int size;
    private int totalPages;
}
