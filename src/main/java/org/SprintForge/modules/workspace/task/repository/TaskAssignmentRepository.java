package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long>, JpaSpecificationExecutor<TaskAssignment> {

    List<TaskAssignment> findByTaskIdAndIsDeletedFalse(Long taskId);

    List<TaskAssignment> findByProjectMemberIdAndIsDeletedFalse(Long projectMemberId);

    Optional<TaskAssignment> findByTaskIdAndProjectMemberIdAndIsDeletedFalse(Long taskId, Long projectMemberId);

    boolean existsByTaskIdAndProjectMemberIdAndIsDeletedFalse(Long taskId, Long projectMemberId);

    long countByTaskIdAndIsDeletedFalse(Long taskId);
}