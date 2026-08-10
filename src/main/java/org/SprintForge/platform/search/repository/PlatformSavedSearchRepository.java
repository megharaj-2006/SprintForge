package org.SprintForge.platform.search.repository;

import org.SprintForge.platform.search.domain.PlatformSavedSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("platformSavedSearchRepository")
public interface PlatformSavedSearchRepository extends JpaRepository<PlatformSavedSearch, Long> {

    @Query("SELECT s FROM PlatformSavedSearch s WHERE s.userId = :userId OR (s.workspaceId = :workspaceId AND s.isShared = true) ORDER BY s.createdAt DESC")
    List<PlatformSavedSearch> findUserAndSharedSearches(@Param("userId") String userId, @Param("workspaceId") String workspaceId);
}
