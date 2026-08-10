package org.SprintForge.modules.management.repository;

import org.SprintForge.modules.management.entity.InstalledPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstalledPackageRepository extends JpaRepository<InstalledPackageEntity, Long> {
    Optional<InstalledPackageEntity> findByPackageKey(String packageKey);
}
