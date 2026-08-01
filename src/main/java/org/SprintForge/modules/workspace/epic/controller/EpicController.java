package org.SprintForge.modules.workspace.epic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.epic.dto.request.EpicMergeRequest;
import org.SprintForge.modules.workspace.epic.dto.request.EpicSplitRequest;
import org.SprintForge.modules.workspace.epic.dto.response.EpicProgressResponse;
import org.SprintForge.modules.workspace.epic.entity.Epic;
import org.SprintForge.modules.workspace.epic.repository.EpicRepository;
import org.SprintForge.modules.workspace.epic.service.EpicApplicationService;
import org.SprintForge.modules.workspace.epic.service.EpicProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Epic Controller", description = "REST endpoints for managing epics and tracking progress")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class EpicController {

    private final EpicRepository epicRepository;
    private final EpicProgressService epicProgressService;
    private final EpicApplicationService epicApplicationService;

    @Operation(summary = "Get epic progress statistics")
    @GetMapping("/epics/{id}/progress")
    public ResponseEntity<EpicProgressResponse> getEpicProgress(@PathVariable Long id) {
        EpicProgressResponse response = epicProgressService.calculateProgress(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Merge an epic into another epic")
    @PostMapping("/epics/{id}/merge")
    public ResponseEntity<Void> mergeEpics(
            @PathVariable Long id,
            @Valid @RequestBody EpicMergeRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        epicApplicationService.mergeEpics(id, request, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Split an epic into a new epic")
    @PostMapping("/epics/{id}/split")
    public ResponseEntity<Epic> splitEpic(
            @PathVariable Long id,
            @Valid @RequestBody EpicSplitRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        Epic response = epicApplicationService.splitEpic(id, request, actorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
