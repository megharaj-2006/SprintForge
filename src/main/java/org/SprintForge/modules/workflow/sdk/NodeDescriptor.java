package org.SprintForge.modules.workflow.sdk;

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
public class NodeDescriptor {
    private String nodeKey;
    private String displayName;
    private String category;
    private String icon;
    private String description;
    @Builder.Default
    private List<String> supportedVersions = List.of("1.0");
    private String configurationSchema;
    private String outputSchema;
    @Builder.Default
    private List<String> tags = new ArrayList<>();
    
    // Typed Ports Contract
    @Builder.Default
    private List<NodeInputPort> inputs = new ArrayList<>();
    @Builder.Default
    private List<NodeOutputPort> outputs = new ArrayList<>();
    @Builder.Default
    private List<NodeBranchPort> branches = new ArrayList<>();
    @Builder.Default
    private NodeCapabilities capabilities = new NodeCapabilities();
}
