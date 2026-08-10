package org.SprintForge.modules.workflow.marketplace;

import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workflow.dto.WorkflowResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow")
@RequiredArgsConstructor
public class MarketplaceController {

    private final WorkflowMarketplaceService marketplaceService;

    @GetMapping("/marketplace")
    public ResponseEntity<List<MarketplaceTemplate>> browseMarketplace(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(marketplaceService.browseMarketplace(category));
    }

    @PostMapping("/marketplace/install")
    public ResponseEntity<WorkflowResponse> installTemplate(@RequestParam String templateKey, @RequestParam(required = false) Long workspaceId) {
        return ResponseEntity.ok(marketplaceService.installTemplate(templateKey, workspaceId));
    }

    @PostMapping("/templates/export")
    public ResponseEntity<String> exportTemplate(@RequestParam Long workflowId) {
        return ResponseEntity.ok(marketplaceService.exportWorkflowTemplate(workflowId));
    }

    @PostMapping("/templates/import")
    public ResponseEntity<WorkflowResponse> importTemplate(@RequestBody String wdlPayload, @RequestParam(required = false) Long workspaceId) {
        return ResponseEntity.ok(marketplaceService.importWorkflowTemplate(wdlPayload, workspaceId));
    }
}
