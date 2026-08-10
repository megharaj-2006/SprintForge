package org.SprintForge.modules.workflow.secret;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow/secrets")
@RequiredArgsConstructor
public class SecretController {

    private final SecretService secretService;

    @Data
    public static class CreateSecretRequest {
        private Long workspaceId;
        private String name;
        private String value;
        private String scope;
    }

    @PostMapping
    public ResponseEntity<WorkflowSecret> createSecret(@RequestBody CreateSecretRequest request) {
        return ResponseEntity.ok(secretService.createSecret(request.getWorkspaceId(), request.getName(), request.getValue(), request.getScope()));
    }

    @GetMapping
    public ResponseEntity<List<WorkflowSecret>> getSecrets(@RequestParam Long workspaceId) {
        return ResponseEntity.ok(secretService.getSecrets(workspaceId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecret(@PathVariable Long id) {
        secretService.deleteSecret(id);
        return ResponseEntity.noContent().build();
    }
}
