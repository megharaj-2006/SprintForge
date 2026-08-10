package org.SprintForge.modules.ai.policy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AIExecutionPolicyRepository extends JpaRepository<AIExecutionPolicy, Long> {
    Optional<AIExecutionPolicy> findByPolicyKey(String policyKey);
}
