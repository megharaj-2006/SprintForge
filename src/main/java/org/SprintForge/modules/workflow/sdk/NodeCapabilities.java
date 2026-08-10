package org.SprintForge.modules.workflow.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeCapabilities {
    private boolean async;
    private boolean retryable;
    private boolean humanInteraction;
    private boolean parallelSafe;
    private boolean transactional;
}
