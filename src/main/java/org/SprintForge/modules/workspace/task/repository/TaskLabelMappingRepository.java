package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskLabelMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLabelMappingRepository extends JpaRepository<TaskLabelMapping, Long>, JpaSpecificationExecutor<TaskLabelMapping> {
}