package org.SprintForge.modules.workflow.marketplace;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.dto.CreateWorkflowRequest;
import org.SprintForge.modules.workflow.dto.WorkflowResponse;
import org.SprintForge.modules.workflow.service.WorkflowManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkflowMarketplaceServiceImpl implements WorkflowMarketplaceService {

    private final MarketplaceTemplateRepository repository;
    private final WorkflowManagementService workflowManagementService;

    @Override
    @Transactional(readOnly = true)
    public List<MarketplaceTemplate> browseMarketplace(String category) {
        if (category != null && !category.isBlank()) {
            return repository.findByCategoryAndPublicVisibleTrue(category);
        }
        return repository.findByPublicVisibleTrue();
    }

    @Override
    @Transactional
    public WorkflowResponse installTemplate(String templateKey, Long targetWorkspaceId) {
        MarketplaceTemplate template = repository.findByTemplateKey(templateKey)
                .orElseThrow(() -> new ResourceNotFoundException("Marketplace template not found: " + templateKey));

        template.setDownloadsCount(template.getDownloadsCount() + 1);
        repository.save(template);

        CreateWorkflowRequest request = new CreateWorkflowRequest();
        request.setName(template.getName());
        request.setDescription(template.getDescription());
        request.setCategory(template.getCategory());
        request.setWorkspaceId(targetWorkspaceId);

        return workflowManagementService.createWorkflow(request);
    }

    @Override
    @Transactional(readOnly = true)
    public String exportWorkflowTemplate(Long workflowId) {
        WorkflowResponse response = workflowManagementService.getWorkflowById(workflowId);
        return "{\"workflowId\":" + response.getId() + ",\"name\":\"" + response.getName() + "\",\"version\":\"1.0\"}";
    }

    @Override
    @Transactional
    public WorkflowResponse importWorkflowTemplate(String wdlPayload, Long targetWorkspaceId) {
        CreateWorkflowRequest request = new CreateWorkflowRequest();
        request.setName("Imported Workflow");
        request.setDescription("Imported from WDL payload");
        request.setCategory("IMPORTED");
        request.setWorkspaceId(targetWorkspaceId);

        return workflowManagementService.createWorkflow(request);
    }
}
