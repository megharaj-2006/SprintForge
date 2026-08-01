package org.SprintForge.modules.workspace.attachment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentSummaryResponse {
    private Long id;
    private String fileName;
    private String contentType;
    private Long size;
    private LocalDateTime createdAt;
}
