package org.SprintForge.modules.workspace.task.repository;

import org.SprintForge.modules.workspace.task.entity.AdvancedTaskRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvancedTaskRelationshipRepository extends JpaRepository<AdvancedTaskRelationship, Long> {

    List<AdvancedTaskRelationship> findBySourceTaskIdAndIsDeletedFalse(Long sourceTaskId);

    List<AdvancedTaskRelationship> findByTargetTaskIdAndIsDeletedFalse(Long targetTaskId);

    List<AdvancedTaskRelationship> findBySourceTaskIdAndRelationshipTypeAndIsDeletedFalse(Long sourceTaskId, String relationshipType);

    List<AdvancedTaskRelationship> findByTargetTaskIdAndRelationshipTypeAndIsDeletedFalse(Long targetTaskId, String relationshipType);

    boolean existsBySourceTaskIdAndTargetTaskIdAndRelationshipTypeAndIsDeletedFalse(Long sourceTaskId, Long targetTaskId, String relationshipType);
}
