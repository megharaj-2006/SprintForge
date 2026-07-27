package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabelRequest {

    @NotBlank(message = "Label name is required")
    @Size(min = 2, max = 50, message = "Label name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Label color is required")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Label color must be a valid hex color code (e.g. #FF5733)")
    private String color;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}