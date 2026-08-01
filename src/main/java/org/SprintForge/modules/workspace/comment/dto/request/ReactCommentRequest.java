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
public class ReactCommentRequest {

    @NotBlank(message = "Reaction emoji cannot be blank")
    @Size(min = 1, max = 100, message = "Reaction emoji must be between 1 and 100 characters")
    private String emoji;
}
