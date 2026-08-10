package org.SprintForge.modules.management.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.management.entity.InstalledPackageEntity;
import org.SprintForge.modules.management.repository.InstalledPackageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageManagerServiceImpl implements PackageManagerService {

    private final InstalledPackageRepository packageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InstalledPackageEntity> getInstalledPackages() {
        return packageRepository.findAll();
    }

    @Override
    @Transactional
    public InstalledPackageEntity installPackage(String packageKey, String packageName, String version) {
        InstalledPackageEntity pkg = packageRepository.findByPackageKey(packageKey).orElseGet(InstalledPackageEntity::new);
        pkg.setPackageKey(packageKey);
        pkg.setPackageName(packageName != null ? packageName : packageKey);
        pkg.setVersionString(version != null ? version : "1.0.0");
        pkg.setStatus("ENABLED");
        pkg.setInstalledBy("ADMIN");
        pkg.setInstalledAt(LocalDateTime.now());

        return packageRepository.save(pkg);
    }

    @Override
    @Transactional
    public InstalledPackageEntity updatePackageStatus(String packageKey, String status) {
        InstalledPackageEntity pkg = packageRepository.findByPackageKey(packageKey)
                .orElseThrow(() -> new ResourceNotFoundException("InstalledPackage not found for key: " + packageKey));
        pkg.setStatus(status);
        return packageRepository.save(pkg);
    }
}
