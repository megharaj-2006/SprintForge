package org.SprintForge.modules.management.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PackageInstalledEvent {
    private final String packageKey;
    private final String version;
}
