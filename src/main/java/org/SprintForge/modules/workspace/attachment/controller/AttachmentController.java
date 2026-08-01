package org.SprintForge.modules.workspace.attachment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.SprintForge.modules.workspace.attachment.dto.request.RenameAttachmentRequest;
import org.SprintForge.modules.workspace.attachment.dto.request.UploadAttachmentRequest;
import org.SprintForge.modules.workspace.attachment.dto.response.AttachmentResponse;
import org.SprintForge.modules.workspace.attachment.service.AttachmentService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
@Tag(name = "Attachment Controller", description = "REST endpoints for managing task attachments")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Operation(summary = "Upload an attachment for a task")
    @PostMapping(value = "/tasks/{taskId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponse> uploadAttachment(
            @PathVariable Long taskId,
            @RequestParam("file") MultipartFile file,
            @RequestPart(value = "metadata", required = false) UploadAttachmentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        if (request == null) {
            request = new UploadAttachmentRequest();
        }
        return ResponseEntity.ok(attachmentService.uploadAttachment(taskId, request, file, actorId));
    }

    @Operation(summary = "Get all attachments for a task")
    @GetMapping("/tasks/{taskId}/attachments")
    public ResponseEntity<List<AttachmentResponse>> getTaskAttachments(
            @PathVariable Long taskId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(attachmentService.getTaskAttachments(taskId, actorId));
    }

    @Operation(summary = "Get metadata of an attachment")
    @GetMapping("/attachments/{attachmentId}")
    public ResponseEntity<AttachmentResponse> getAttachmentMetadata(
            @PathVariable Long attachmentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(attachmentService.getAttachment(attachmentId, actorId));
    }

    @Operation(summary = "Download attachment file content")
    @GetMapping("/attachments/{attachmentId}/download")
    public ResponseEntity<Resource> downloadAttachment(
            @PathVariable Long attachmentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        Resource resource = attachmentService.downloadAttachment(attachmentId, actorId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Operation(summary = "Rename an attachment")
    @PatchMapping("/attachments/{attachmentId}")
    public ResponseEntity<AttachmentResponse> renameAttachment(
            @PathVariable Long attachmentId,
            @Valid @RequestBody RenameAttachmentRequest request,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(attachmentService.renameAttachment(attachmentId, request, actorId));
    }

    @Operation(summary = "Archive an attachment")
    @PatchMapping("/attachments/{attachmentId}/archive")
    public ResponseEntity<AttachmentResponse> archiveAttachment(
            @PathVariable Long attachmentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(attachmentService.archiveAttachment(attachmentId, actorId));
    }

    @Operation(summary = "Restore an archived attachment")
    @PatchMapping("/attachments/{attachmentId}/restore")
    public ResponseEntity<AttachmentResponse> restoreAttachment(
            @PathVariable Long attachmentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        return ResponseEntity.ok(attachmentService.restoreAttachment(attachmentId, actorId));
    }

    @Operation(summary = "Delete an attachment")
    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable Long attachmentId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") Long actorId) {
        attachmentService.deleteAttachment(attachmentId, actorId);
        return ResponseEntity.noContent().build();
    }
}
