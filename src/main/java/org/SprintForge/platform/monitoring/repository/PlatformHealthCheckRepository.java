package org.SprintForge.platform.monitoring.repository;

import org.SprintForge.platform.monitoring.domain.HealthCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platformHealthCheckRepository")
public interface PlatformHealthCheckRepository extends JpaRepository<HealthCheck, Long> {
    List<HealthCheck> findByComponentOrderByCheckedAtDesc(String component);
}
