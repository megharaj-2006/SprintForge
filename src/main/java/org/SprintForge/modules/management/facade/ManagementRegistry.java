package org.SprintForge.modules.management.facade;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.management.sdk.ManagedPlatformModule;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ManagementRegistry {

    private final Map<String, ManagedPlatformModule> moduleRegistry = new ConcurrentHashMap<>();

    public ManagementRegistry(List<ManagedPlatformModule> modules) {
        if (modules != null) {
            for (ManagedPlatformModule module : modules) {
                registerModule(module);
            }
        }
    }

    public void registerModule(ManagedPlatformModule module) {
        log.info("Registering ManagedPlatformModule: {} [{}]", module.moduleName(), module.moduleId());
        moduleRegistry.put(module.moduleId().toLowerCase(), module);
    }

    public ManagedPlatformModule getModule(String moduleId) {
        ManagedPlatformModule module = moduleRegistry.get(moduleId.toLowerCase());
        if (module == null) {
            throw new ResourceNotFoundException("ManagedPlatformModule not found for moduleId: " + moduleId);
        }
        return module;
    }

    public Optional<ManagedPlatformModule> findModule(String moduleId) {
        return Optional.ofNullable(moduleRegistry.get(moduleId != null ? moduleId.toLowerCase() : ""));
    }

    public List<ManagedPlatformModule> getAllModules() {
        return List.copyOf(moduleRegistry.values());
    }
}
