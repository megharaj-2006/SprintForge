package org.SprintForge.modules.integration.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorDescriptor {
    private String connectorKey;
    private String displayName;
    private String category;
    private String version;
    private String description;
    @Builder.Default
    private List<String> supportedCapabilities = new ArrayList<>();
    @Builder.Default
    private List<String> authenticationTypes = new ArrayList<>();
}
