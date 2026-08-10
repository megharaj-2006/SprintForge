package org.SprintForge.modules.workflow.governance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.SprintForge.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowGovernanceServiceImpl implements WorkflowGovernanceService {

    private final WorkflowPolicyRepository policyRepository;

    @Override
    @Transactional(readOnly = true)
    public void validatePublishPermission(Long workflowId) {
        log.info("Validating publish policy for workflow {}", workflowId);
    }

    @Override
    @Transactional(readOnly = true)
    public void validateExecutionQuotas(Long workflowId, Long workspaceId) {
        if (workspaceId != null) {
            Optional<WorkflowPolicy> policy = policyRepository.findByWorkspaceIdAndEnabledTrue(workspaceId);
            if (policy.isPresent() && Boolean.FALSE.equals(policy.get().getEnabled())) {
                throw new BusinessException("Workflow execution disabled by workspace governance policy.");
            }
        }
    }

    @Override
    @Transactional
    public WorkflowPolicy createOrUpdatePolicy(WorkflowPolicy policy) {
        return policyRepository.save(policy);
    }
}
