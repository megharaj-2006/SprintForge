package org.SprintForge.modules.workspace.sprint.repository;

import org.SprintForge.modules.workspace.sprint.entity.SprintStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintStatisticsRepository extends JpaRepository<SprintStatistics, Long>, JpaSpecificationExecutor<SprintStatistics> {

    Optional<SprintStatistics> findBySprintIdAndIsDeletedFalse(Long sprintId);
}