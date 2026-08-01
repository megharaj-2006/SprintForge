package org.SprintForge.modules.workspace.bookmark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSavedViewRequest {

    private String name;

    private String description;

    private String viewType;

    private String filters;

    private String sorting;

    private String grouping;

    private String columns;

    private String layout;

    private String visibility;

    private Boolean isDefault;

    private Boolean isShared;
}
