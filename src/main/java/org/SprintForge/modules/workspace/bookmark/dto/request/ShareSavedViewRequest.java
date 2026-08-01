package org.SprintForge.modules.workspace.bookmark.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareSavedViewRequest {

    @NotNull(message = "isShared is required")
    private Boolean isShared;

    private String visibility; // PRIVATE, WORKSPACE, PROJECT
}
