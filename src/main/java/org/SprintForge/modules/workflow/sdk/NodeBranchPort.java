package org.SprintForge.modules.workflow.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeBranchPort {
    private String label;
    private String description;

    public static NodeBranchPortBuilder label(String label) {
        return builder().label(label);
    }
}
