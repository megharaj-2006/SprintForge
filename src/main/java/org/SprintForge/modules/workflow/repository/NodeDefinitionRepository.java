package org.SprintForge.modules.workflow.repository;

import org.SprintForge.modules.workflow.entity.NodeDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NodeDefinitionRepository extends JpaRepository<NodeDefinition, Long> {
    Optional<NodeDefinition> findByNodeKey(String nodeKey);
    List<NodeDefinition> findByEnabledTrue();
}
