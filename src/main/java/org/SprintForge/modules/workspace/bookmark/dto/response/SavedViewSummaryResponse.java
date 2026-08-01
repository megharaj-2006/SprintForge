package org.SprintForge.modules.workspace.bookmark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedViewSummaryResponse {

    private Long id;
    private Long projectId;
    private String name;
    private String viewType;
    private Boolean isDefault;
    private Boolean isShared;
    private Boolean isFavorite;
}
