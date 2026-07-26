package org.SprintForge.modules.workspace.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.SprintForge.modules.workspace.workspace.entity.enums.WorkspaceVisibility;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceFilterRequest {

    private List<WorkspaceVisibility> visibilities;
    private Boolean isArchived;
    private List<Long> ownerIds;
    private Long minStorageUsed;
    private Long maxStorageUsed;
    private Integer minMembers;
    private Integer maxMembers;
}
