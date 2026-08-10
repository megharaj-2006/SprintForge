package org.SprintForge.modules.workflow.plugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class PluginRegistry {

    private final Map<String, PluginManifest> installedPlugins = new ConcurrentHashMap<>();

    public void registerPlugin(PluginManifest manifest) {
        log.info("Registering dynamic workflow plugin package: {} (v{})", manifest.getPackageId(), manifest.getPluginVersion());
        installedPlugins.put(manifest.getPackageId(), manifest);
    }

    public Map<String, PluginManifest> getInstalledPlugins() {
        return installedPlugins;
    }
}
