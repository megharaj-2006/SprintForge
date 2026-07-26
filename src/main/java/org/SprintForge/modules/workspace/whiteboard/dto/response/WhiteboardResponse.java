package org.SprintForge.modules.workspace.whiteboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WhiteboardResponse {

    private Long id;
    private Long workspaceId;
    private Long projectId;
    private String title;
    private String description;
    private String boardData;
    private Long createdByUserId;
    private Long updatedByUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
