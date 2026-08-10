package org.SprintForge.modules.management.repository;

import org.SprintForge.modules.management.entity.LicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseEntity, Long> {
    Optional<LicenseEntity> findByLicenseKey(String licenseKey);
}
