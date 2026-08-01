package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskVersionRepository extends JpaRepository<TaskVersion, Long> {

    List<TaskVersion> findByTaskIdAndIsDeletedFalseOrderByVersionNumberDesc(Long taskId);

    Optional<TaskVersion> findFirstByTaskIdAndIsDeletedFalseOrderByVersionNumberDesc(Long taskId);
}
