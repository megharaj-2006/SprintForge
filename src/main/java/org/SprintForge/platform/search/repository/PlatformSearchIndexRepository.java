package org.SprintForge.platform.search.repository;

import org.SprintForge.platform.search.domain.PlatformSearchIndex;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("platformSearchIndexRepository")
public interface PlatformSearchIndexRepository extends JpaRepository<PlatformSearchIndex, Long>, JpaSpecificationExecutor<PlatformSearchIndex> {

    Optional<PlatformSearchIndex> findByEntityTypeAndEntityId(String entityType, String entityId);

    void deleteByEntityTypeAndEntityId(String entityType, String entityId);

    void deleteByEntityType(String entityType);

    @Query("SELECT s FROM PlatformSearchIndex s WHERE (s.workspaceId = :workspaceId OR :workspaceId IS NULL) " +
           "AND (LOWER(s.title) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.content) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(s.keywords) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "ORDER BY s.lastIndexedAt DESC")
    List<PlatformSearchIndex> searchGlobal(@Param("query") String query, @Param("workspaceId") String workspaceId, Pageable pageable);

    @Query("SELECT DISTINCT s.title FROM PlatformSearchIndex s WHERE LOWER(s.title) LIKE LOWER(CONCAT(:prefix, '%'))")
    List<String> findSuggestions(@Param("prefix") String prefix, Pageable pageable);
}
