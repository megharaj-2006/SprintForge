package org.SprintForge.modules.workflow.catalog;

import lombok.RequiredArgsConstructor;
import org.SprintForge.common.exception.ResourceNotFoundException;
import org.SprintForge.modules.workflow.executor.NodeTypeRegistry;
import org.SprintForge.modules.workflow.sdk.NodeDescriptor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow/nodes")
@RequiredArgsConstructor
public class NodeCatalogController {

    private final NodeTypeRegistry nodeTypeRegistry;

    @GetMapping
    public ResponseEntity<List<NodeDescriptor>> getAllNodes() {
        return ResponseEntity.ok(nodeTypeRegistry.getAllDescriptors());
    }

    @GetMapping("/{key}")
    public ResponseEntity<NodeDescriptor> getNodeByKey(@PathVariable String key) {
        return nodeTypeRegistry.getDescriptorByKey(key)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("NodeDescriptor not found for key: " + key));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<NodeDescriptor>> getNodesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(nodeTypeRegistry.getDescriptorsByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NodeDescriptor>> searchNodes(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(nodeTypeRegistry.searchDescriptors(q));
    }
}
