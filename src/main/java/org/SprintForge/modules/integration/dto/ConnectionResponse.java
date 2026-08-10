package org.SprintForge.modules.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionResponse {
    private Long id;
    private String connectionName;
    private String providerKey;
    private String status;
    private String healthStatus;
    private String baseUrl;
}
