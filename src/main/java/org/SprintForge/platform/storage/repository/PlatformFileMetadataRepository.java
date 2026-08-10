package org.SprintForge.platform.storage.repository;

import org.SprintForge.platform.storage.domain.PlatformFileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("platformFileMetadataRepository")
public interface PlatformFileMetadataRepository extends JpaRepository<PlatformFileMetadata, Long> {

    List<PlatformFileMetadata> findByStoredFileId(Long storedFileId);

    Optional<PlatformFileMetadata> findByStoredFileIdAndMetaKey(Long storedFileId, String metaKey);

    void deleteByStoredFileId(Long storedFileId);
}
