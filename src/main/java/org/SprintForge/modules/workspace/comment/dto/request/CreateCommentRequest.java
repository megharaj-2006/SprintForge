package org.SprintForge.modules.workspace.comment.dto.request;

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
public class CreateCommentRequest {

    @NotBlank(message = "Comment content cannot be blank")
    @Size(min = 1, max = 10000, message = "Comment content must be between 1 and 10000 characters")
    private String content;
}
