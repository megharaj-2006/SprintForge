package org.SprintForge.modules.workflow.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowTemplate {
    private String id;
    private String name;
    private String description;
    private String category;
    private List<TemplateNode> nodes;
    private List<TemplateConnection> connections;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TemplateNode {
        private String nodeKey;
        private String name;
        private Double x;
        private Double y;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TemplateConnection {
        private String sourceNodeName;
        private String targetNodeName;
        private String branchLabel;
    }
}
