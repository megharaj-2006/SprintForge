package org.SprintForge.modules.workspace.sprint.repository;

import org.SprintForge.modules.workspace.sprint.entity.Sprint;
import org.SprintForge.modules.workspace.sprint.entity.enums.SprintStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long>, JpaSpecificationExecutor<Sprint> {

    Optional<Sprint> findByIdAndIsDeletedFalse(Long id);

    List<Sprint> findByProjectIdAndIsDeletedFalse(Long projectId);

    List<Sprint> findByProjectIdAndIsDeletedFalseOrderByOrderIndexAsc(Long projectId);

    List<Sprint> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, SprintStatus status);

    boolean existsByProjectIdAndStatusAndIsDeletedFalse(Long projectId, SprintStatus status);

    long countByProjectIdAndIsDeletedFalse(Long projectId);
}