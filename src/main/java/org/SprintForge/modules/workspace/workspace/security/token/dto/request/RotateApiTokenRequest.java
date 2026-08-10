package org.SprintForge.modules.workspace.workspace.security.token.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RotateApiTokenRequest {
    private String name;
}
