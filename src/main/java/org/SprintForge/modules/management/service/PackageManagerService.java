package org.SprintForge.modules.management.service;

import org.SprintForge.modules.management.entity.InstalledPackageEntity;

import java.util.List;

public interface PackageManagerService {
    List<InstalledPackageEntity> getInstalledPackages();
    InstalledPackageEntity installPackage(String packageKey, String packageName, String version);
    InstalledPackageEntity updatePackageStatus(String packageKey, String status);
}
