package org.SprintForge.platform.analytics.repository;

import org.SprintForge.platform.analytics.domain.AnalyticsSnapshot;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platformAnalyticsSnapshotRepository")
public interface PlatformAnalyticsSnapshotRepository extends JpaRepository<AnalyticsSnapshot, Long> {

    List<AnalyticsSnapshot> findByWorkspaceIdAndSnapshotType(String workspaceId, String snapshotType, Pageable pageable);

    List<AnalyticsSnapshot> findByProjectIdAndSnapshotType(String projectId, String snapshotType, Pageable pageable);
}
