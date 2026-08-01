package org.SprintForge.modules.workspace.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentSummaryResponse {
    private Long id;
    private Long taskId;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
