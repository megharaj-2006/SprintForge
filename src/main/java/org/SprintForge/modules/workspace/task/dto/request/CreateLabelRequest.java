package org.SprintForge.modules.workspace.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabelRequest {

    @NotNull
    private Long projectId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @Size(max = 20)
    private String color;
}