package org.SprintForge.platform.search.repository;

import org.SprintForge.platform.search.domain.PlatformSearchHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository("platformSearchHistoryRepository")
public interface PlatformSearchHistoryRepository extends JpaRepository<PlatformSearchHistory, Long> {

    List<PlatformSearchHistory> findByUserIdOrderBySearchedAtDesc(String userId, Pageable pageable);

    @Query("SELECT h.queryText FROM PlatformSearchHistory h GROUP BY h.queryText ORDER BY COUNT(h) DESC")
    List<String> findPopularQueries(Pageable pageable);

    void deleteBySearchedAtBefore(Instant timestamp);
}
