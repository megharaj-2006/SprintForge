package org.SprintForge.modules.integration.dto;

import lombok.Data;

@Data
public class CreateConnectionRequest {
    private Long workspaceId;
    private String providerKey;
    private String connectionName;
    private String authenticationType;
    private String baseUrl;
    private String credentialValue;
}
