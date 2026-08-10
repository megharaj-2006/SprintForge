package org.SprintForge.modules.workflow.marketplace;

import org.SprintForge.modules.workflow.dto.WorkflowResponse;

import java.util.List;

public interface WorkflowMarketplaceService {
    List<MarketplaceTemplate> browseMarketplace(String category);
    WorkflowResponse installTemplate(String templateKey, Long targetWorkspaceId);
    String exportWorkflowTemplate(Long workflowId);
    WorkflowResponse importWorkflowTemplate(String wdlPayload, Long targetWorkspaceId);
}
