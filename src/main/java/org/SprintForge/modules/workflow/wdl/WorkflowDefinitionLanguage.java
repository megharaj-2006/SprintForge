package org.SprintForge.modules.workflow.wdl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDefinitionLanguage {
    @Builder.Default
    private String wdlVersion = "1.0.0";
    private Metadata metadata;
    @Builder.Default
    private List<WdlNode> nodes = new ArrayList<>();
    @Builder.Default
    private List<WdlConnection> connections = new ArrayList<>();
    @Builder.Default
    private List<WdlVariable> variables = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private String name;
        private String description;
        private String category;
        private String author;
        private Long workspaceId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WdlNode {
        private String id;
        private String nodeKey;
        private String name;
        private String nodeType;
        private Double x;
        private Double y;
        @Builder.Default
        private Map<String, Object> configuration = new HashMap<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WdlConnection {
        private String sourceNodeId;
        private String targetNodeId;
        private String branchLabel;
        private Integer priority;
        private String conditionExpression;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WdlVariable {
        private String name;
        private String type;
        private String scope;
        private String defaultValue;
        private Boolean required;
    }
}
