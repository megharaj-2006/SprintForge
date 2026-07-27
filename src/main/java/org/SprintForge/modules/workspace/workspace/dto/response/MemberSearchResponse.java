package org.SprintForge.modules.workspace.workspace.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchResponse {

    private List<WorkspaceMemberResponse> members;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
