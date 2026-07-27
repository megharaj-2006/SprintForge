package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.TaskLabelMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskLabelMappingRepository extends JpaRepository<TaskLabelMapping, Long>, JpaSpecificationExecutor<TaskLabelMapping> {

    boolean existsByTaskIdAndLabelId(Long taskId, Long labelId);

    Optional<TaskLabelMapping> findByTaskIdAndLabelId(Long taskId, Long labelId);

    List<TaskLabelMapping> findByTaskId(Long taskId);

    @Query("SELECT tlm.labelId FROM TaskLabelMapping tlm WHERE tlm.taskId = :taskId AND tlm.isDeleted = false")
    List<Long> findLabelIdsByTaskId(@Param("taskId") Long taskId);

    @Query("SELECT tlm.taskId FROM TaskLabelMapping tlm WHERE tlm.labelId = :labelId AND tlm.isDeleted = false")
    List<Long> findTaskIdsByLabelId(@Param("labelId") Long labelId);

    @Modifying
    @Query("DELETE FROM TaskLabelMapping tlm WHERE tlm.taskId = :taskId")
    void deleteByTaskId(@Param("taskId") Long taskId);

    @Modifying
    @Query("DELETE FROM TaskLabelMapping tlm WHERE tlm.labelId = :labelId")
    void deleteByLabelId(@Param("labelId") Long labelId);

}