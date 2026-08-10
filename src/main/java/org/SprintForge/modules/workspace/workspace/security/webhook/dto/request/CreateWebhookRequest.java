package org.SprintForge.modules.workspace.workspace.security.webhook.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWebhookRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "https?://.*", message = "Must be a valid URL")
    private String url;
    private List<String> events;
    private String secret;
}
