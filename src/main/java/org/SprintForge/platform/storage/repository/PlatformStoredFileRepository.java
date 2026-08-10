package org.SprintForge.platform.storage.repository;

import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("platformStoredFileRepository")
public interface PlatformStoredFileRepository extends JpaRepository<PlatformStoredFile, Long> {

    List<PlatformStoredFile> findByWorkspaceIdAndDeletedFalse(String workspaceId, Pageable pageable);

    List<PlatformStoredFile> findByOwnerIdAndDeletedFalse(String ownerId, Pageable pageable);

    Optional<PlatformStoredFile> findByChecksumAndDeletedFalse(String checksum);

    @Query("SELECT COALESCE(SUM(f.sizeBytes), 0) FROM PlatformStoredFile f WHERE f.workspaceId = :workspaceId AND f.isDeleted = false")
    long calculateUsedSpaceByWorkspace(@Param("workspaceId") String workspaceId);

    @Query("SELECT f FROM PlatformStoredFile f WHERE f.isDeleted = false ORDER BY f.sizeBytes DESC")
    List<PlatformStoredFile> findLargestFiles(Pageable pageable);

    @Query("SELECT f.checksum FROM PlatformStoredFile f WHERE f.isDeleted = false GROUP BY f.checksum HAVING COUNT(f) > 1")
    List<String> findDuplicateChecksums();
}
