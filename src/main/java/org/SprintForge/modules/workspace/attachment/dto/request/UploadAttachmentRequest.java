package org.SprintForge.modules.workspace.attachment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadAttachmentRequest {
    private String description;
    private String checksum;
}
