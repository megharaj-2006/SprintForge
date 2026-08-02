package org.SprintForge.modules.workspace.project.keyresult.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.CreateKeyResultRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.UpdateKeyResultProgressRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.request.UpdateKeyResultRequest;
import org.SprintForge.modules.workspace.project.keyresult.dto.response.KeyResultForecastResponse;
import org.SprintForge.modules.workspace.project.keyresult.dto.response.KeyResultResponse;
import org.SprintForge.modules.workspace.project.keyresult.service.KeyResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("strategicKeyResultController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Key Result Controller", description = "REST endpoints for managing objective key results")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class KeyResultController {

    private final KeyResultService keyResultService;

    @Operation(summary = "Create a key result under an objective")
    @PostMapping("/objectives/{objectiveId}/key-results")
    public ResponseEntity<KeyResultResponse> createKeyResult(
            @PathVariable("objectiveId") Long objectiveId,
            @Valid @RequestBody CreateKeyResultRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(keyResultService.createKeyResult(objectiveId, request, actorId));
    }

    @Operation(summary = "Get all key results for an objective")
    @GetMapping("/objectives/{objectiveId}/key-results")
    public ResponseEntity<List<KeyResultResponse>> getKeyResults(@PathVariable("objectiveId") Long objectiveId) {
        return ResponseEntity.ok(keyResultService.getKeyResults(objectiveId));
    }

    @Operation(summary = "Get key result details by ID")
    @GetMapping("/key-results/{keyResultId}")
    public ResponseEntity<KeyResultResponse> getKeyResult(@PathVariable("keyResultId") Long keyResultId) {
        return ResponseEntity.ok(keyResultService.getKeyResult(keyResultId));
    }

    @Operation(summary = "Update key result details")
    @PatchMapping("/key-results/{keyResultId}")
    public ResponseEntity<KeyResultResponse> updateKeyResult(
            @PathVariable("keyResultId") Long keyResultId,
            @Valid @RequestBody UpdateKeyResultRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(keyResultService.updateKeyResult(keyResultId, request, actorId));
    }

    @Operation(summary = "Update key result progress metric")
    @PatchMapping("/key-results/{keyResultId}/progress")
    public ResponseEntity<KeyResultResponse> updateProgress(
            @PathVariable("keyResultId") Long keyResultId,
            @Valid @RequestBody UpdateKeyResultProgressRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(keyResultService.updateProgress(keyResultId, request, actorId));
    }

    @Operation(summary = "Delete a key result")
    @DeleteMapping("/key-results/{keyResultId}")
    public ResponseEntity<Void> deleteKeyResult(
            @PathVariable("keyResultId") Long keyResultId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        keyResultService.deleteKeyResult(keyResultId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get key result forecast")
    @GetMapping("/key-results/{keyResultId}/forecast")
    public ResponseEntity<KeyResultForecastResponse> getForecast(@PathVariable("keyResultId") Long keyResultId) {
        return ResponseEntity.ok(keyResultService.getForecast(keyResultId));
    }
}
