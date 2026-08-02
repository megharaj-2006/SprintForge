package org.SprintForge.modules.workspace.project.governance.document.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.CreateDocumentRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.CreateFolderRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.request.UpdateDocumentRequest;
import org.SprintForge.modules.workspace.project.governance.document.dto.response.DocumentResponse;
import org.SprintForge.modules.workspace.project.governance.document.dto.response.DocumentVersionResponse;
import org.SprintForge.modules.workspace.project.governance.document.entity.DocumentFolder;
import org.SprintForge.modules.workspace.project.governance.document.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("governanceDocumentController")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Document Controller", description = "REST endpoints for managing project documentation, folders, and markdown version history")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class DocumentController {

    private final DocumentService documentService;

    @Operation(summary = "Create a project document")
    @PostMapping("/projects/{projectId}/documents")
    public ResponseEntity<DocumentResponse> createDocument(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateDocumentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createDocument(projectId, request, actorId));
    }

    @Operation(summary = "Get all documents for a project (optional folder filter)")
    @GetMapping("/projects/{projectId}/documents")
    public ResponseEntity<List<DocumentResponse>> getDocuments(
            @PathVariable("projectId") Long projectId,
            @RequestParam(value = "folderId", required = false) Long folderId) {
        return ResponseEntity.ok(documentService.getDocuments(projectId, folderId));
    }

    @Operation(summary = "Get document by ID")
    @GetMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable("documentId") Long documentId) {
        return ResponseEntity.ok(documentService.getDocument(documentId));
    }

    @Operation(summary = "Update document content or metadata")
    @PatchMapping("/documents/{documentId}")
    public ResponseEntity<DocumentResponse> updateDocument(
            @PathVariable("documentId") Long documentId,
            @Valid @RequestBody UpdateDocumentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(documentService.updateDocument(documentId, request, actorId));
    }

    @Operation(summary = "Delete / archive document")
    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable("documentId") Long documentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        documentService.deleteDocument(documentId, actorId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Restore a previous version of a document")
    @PostMapping("/documents/{documentId}/restore")
    public ResponseEntity<DocumentResponse> restoreVersion(
            @PathVariable("documentId") Long documentId,
            @RequestParam("versionNumber") Integer versionNumber,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(documentService.restoreVersion(documentId, versionNumber, actorId));
    }

    @Operation(summary = "Toggle favorite status of a document")
    @PostMapping("/documents/{documentId}/favorite")
    public ResponseEntity<DocumentResponse> toggleFavorite(
            @PathVariable("documentId") Long documentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(documentService.toggleFavorite(documentId, actorId));
    }

    @Operation(summary = "Toggle pin status of a document")
    @PostMapping("/documents/{documentId}/pin")
    public ResponseEntity<DocumentResponse> togglePin(
            @PathVariable("documentId") Long documentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(documentService.togglePin(documentId, actorId));
    }

    @Operation(summary = "Get document version history")
    @GetMapping("/documents/{documentId}/versions")
    public ResponseEntity<List<DocumentVersionResponse>> getDocumentVersions(@PathVariable("documentId") Long documentId) {
        return ResponseEntity.ok(documentService.getDocumentVersions(documentId));
    }

    @Operation(summary = "Create a document folder")
    @PostMapping("/projects/{projectId}/document-folders")
    public ResponseEntity<DocumentFolder> createFolder(
            @PathVariable("projectId") Long projectId,
            @Valid @RequestBody CreateFolderRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.createFolder(projectId, request, actorId));
    }

    @Operation(summary = "Get all document folders for a project")
    @GetMapping("/projects/{projectId}/document-folders")
    public ResponseEntity<List<DocumentFolder>> getFolders(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(documentService.getFolders(projectId));
    }
}
