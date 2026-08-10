package org.SprintForge.platform.storage.repository;

import org.SprintForge.platform.storage.domain.PlatformFileVersion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("platformFileVersionRepository")
public interface PlatformFileVersionRepository extends JpaRepository<PlatformFileVersion, Long> {

    List<PlatformFileVersion> findByStoredFileIdOrderByVersionNumberDesc(Long storedFileId);

    Optional<PlatformFileVersion> findByStoredFileIdAndVersionNumber(Long storedFileId, int versionNumber);
}
