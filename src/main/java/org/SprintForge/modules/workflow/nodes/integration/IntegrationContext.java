package org.SprintForge.modules.workflow.nodes.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationContext {
    private String systemName;
    private String connectionId;
    private String credentialRef;
    private String baseUrl;
}
