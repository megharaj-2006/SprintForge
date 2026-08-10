package org.SprintForge.modules.integration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConnectorPackageInstalledEvent {
    private final String packageKey;
    private final String version;
}
