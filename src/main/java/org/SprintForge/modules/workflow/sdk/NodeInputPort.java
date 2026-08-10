package org.SprintForge.modules.workflow.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeInputPort {
    private String name;
    private String type;
    private String description;
    private boolean required;
    private Object defaultValue;
}
