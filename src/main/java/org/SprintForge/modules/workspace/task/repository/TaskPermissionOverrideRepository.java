package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskPermissionOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskPermissionOverrideRepository extends JpaRepository<TaskPermissionOverride, Long> {

    List<TaskPermissionOverride> findByTaskIdAndUserIdAndIsDeletedFalse(Long taskId, Long userId);

    List<TaskPermissionOverride> findByTaskIdAndIsDeletedFalse(Long taskId);

    Optional<TaskPermissionOverride> findByTaskIdAndUserIdAndPermissionAndIsDeletedFalse(Long taskId, Long userId, String permission);
}
