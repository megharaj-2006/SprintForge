package org.SprintForge.modules.management.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.management.entity.FeatureFlagEntity;
import org.SprintForge.modules.management.entity.InstalledPackageEntity;
import org.SprintForge.modules.management.entity.LicenseEntity;
import org.SprintForge.modules.management.facade.PlatformManagementFacade;
import org.SprintForge.modules.management.repository.LicenseRepository;
import org.SprintForge.modules.management.sdk.HealthReport;
import org.SprintForge.modules.management.sdk.ManagementDescriptor;
import org.SprintForge.modules.management.sdk.MetricsSnapshot;
import org.SprintForge.modules.management.service.FeatureFlagService;
import org.SprintForge.modules.management.service.PackageManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class PlatformManagementController {

    private final PlatformManagementFacade facade;
    private final FeatureFlagService featureFlagService;
    private final PackageManagerService packageManagerService;
    private final LicenseRepository licenseRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(facade.getDashboardOverview());
    }

    @GetMapping("/modules")
    public ResponseEntity<List<ManagementDescriptor>> getModules() {
        return ResponseEntity.ok(facade.getModules());
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, HealthReport>> getHealth() {
        return ResponseEntity.ok(facade.getAggregatedHealth());
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, MetricsSnapshot>> getMetrics() {
        return ResponseEntity.ok(facade.getAggregatedMetrics());
    }

    @GetMapping("/configuration")
    public ResponseEntity<Map<String, Object>> getConfiguration() {
        return ResponseEntity.ok(Map.of("systemProfile", "ENTERPRISE", "dynamicReloadEnabled", true));
    }

    @PatchMapping("/configuration")
    public ResponseEntity<Map<String, Object>> updateConfiguration(@RequestBody Map<String, Object> configUpdates) {
        return ResponseEntity.ok(Map.of("updated", true, "config", configUpdates));
    }

    @GetMapping("/packages")
    public ResponseEntity<List<InstalledPackageEntity>> getPackages() {
        return ResponseEntity.ok(packageManagerService.getInstalledPackages());
    }

    @Data
    public static class InstallPackageRequest {
        private String packageKey;
        private String packageName;
        private String version;
    }

    @PostMapping("/packages/install")
    public ResponseEntity<InstalledPackageEntity> installPackage(@RequestBody InstallPackageRequest request) {
        return ResponseEntity.ok(packageManagerService.installPackage(request.getPackageKey(), request.getPackageName(), request.getVersion()));
    }

    @PostMapping("/packages/enable")
    public ResponseEntity<InstalledPackageEntity> enablePackage(@RequestParam String packageKey) {
        return ResponseEntity.ok(packageManagerService.updatePackageStatus(packageKey, "ENABLED"));
    }

    @PostMapping("/packages/disable")
    public ResponseEntity<InstalledPackageEntity> disablePackage(@RequestParam String packageKey) {
        return ResponseEntity.ok(packageManagerService.updatePackageStatus(packageKey, "DISABLED"));
    }

    @GetMapping("/feature-flags")
    public ResponseEntity<List<FeatureFlagEntity>> getFeatureFlags() {
        return ResponseEntity.ok(featureFlagService.getFeatureFlags());
    }

    @Data
    public static class FeatureFlagUpdateRequest {
        private String flagKey;
        private boolean enabled;
        private Integer rolloutPercentage;
    }

    @PatchMapping("/feature-flags")
    public ResponseEntity<FeatureFlagEntity> updateFeatureFlag(@RequestBody FeatureFlagUpdateRequest request) {
        return ResponseEntity.ok(featureFlagService.updateFeatureFlag(request.getFlagKey(), request.isEnabled(), request.getRolloutPercentage()));
    }

    @GetMapping("/license")
    public ResponseEntity<LicenseEntity> getLicense() {
        LicenseEntity license = licenseRepository.findAll().stream().findFirst().orElse(null);
        return ResponseEntity.ok(license);
    }
}
