package org.SprintForge.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameCheckResponse {
    private String username;

    @JsonProperty("isAvailable")
    private boolean isAvailable;

    private String message;
}
