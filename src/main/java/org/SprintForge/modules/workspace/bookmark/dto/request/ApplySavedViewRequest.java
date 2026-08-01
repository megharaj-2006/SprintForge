package org.SprintForge.modules.workspace.bookmark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplySavedViewRequest {

    private String searchOverride;
    private Integer page;
    private Integer size;
}
