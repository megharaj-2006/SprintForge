package org.SprintForge.modules.workspace.attachment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenameAttachmentRequest {

    @NotBlank(message = "File name cannot be blank")
    @Size(min = 1, max = 255, message = "File name must be between 1 and 255 characters")
    private String fileName;
}
