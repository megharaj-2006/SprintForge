package org.SprintForge.modules.workspace.project.release.repository;

import org.SprintForge.modules.workspace.project.release.entity.Release;
import org.SprintForge.modules.workspace.project.release.entity.enums.ReleaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("strategicReleaseRepository")
public interface ReleaseRepository extends JpaRepository<Release, Long>, JpaSpecificationExecutor<Release> {

    List<Release> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<Release> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ReleaseStatus status);

    Optional<Release> findByProjectIdAndReleaseVersionAndIsDeletedFalse(Long projectId, String releaseVersion);

    boolean existsByProjectIdAndReleaseVersionAndIsDeletedFalse(Long projectId, String releaseVersion);

    boolean existsByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ReleaseStatus status);
}
