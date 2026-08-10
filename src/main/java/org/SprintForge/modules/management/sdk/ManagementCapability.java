package org.SprintForge.modules.management.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagementCapability {
    private String capabilityKey;
    private String name;
    private String description;
    private boolean enabled;
}
