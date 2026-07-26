package org.SprintForge.modules.admin.repository;

import org.SprintForge.modules.admin.entity.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration, Long>, JpaSpecificationExecutor<SystemConfiguration> {
}