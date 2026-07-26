package org.SprintForge.modules.workspace.workspace.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceInviteRequest {

    @NotEmpty(message = "At least one email is required for invitation")
    private List<@Email(message = "Invalid email format") String> emails;

    private Long roleId;

    @Size(max = 500, message = "Invitation message must not exceed 500 characters")
    private String message;

    private Integer expirationDays;
}
