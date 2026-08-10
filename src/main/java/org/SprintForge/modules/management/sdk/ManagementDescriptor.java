package org.SprintForge.modules.management.sdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagementDescriptor {
    private String moduleId;
    private String moduleName;
    private String version;
    private String category;
    private String description;
}
