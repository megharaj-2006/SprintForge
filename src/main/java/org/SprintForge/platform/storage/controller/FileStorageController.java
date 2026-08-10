package org.SprintForge.platform.storage.controller;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.storage.dto.*;
import org.SprintForge.platform.storage.service.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("platformFileStorageController")
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileStorageController {

    private final UploadService uploadService;
    private final DownloadService downloadService;
    private final StorageService storageService;
    private final FileVersionService versionService;
    private final FileMetadataService metadataService;
    private final QuotaService quotaService;

    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestBody FileUploadRequest request) {
        return ResponseEntity.ok(uploadService.uploadFile(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoredFileResponse> getFileDetails(@PathVariable("id") Long id) {
        return ResponseEntity.ok(storageService.getFileDetails(id));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable("id") Long id,
            @RequestParam(value = "userId", defaultValue = "system") String userId) {
        byte[] content = downloadService.downloadFile(id, userId);
        StoredFileResponse file = storageService.getFileDetails(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalName() + "\"")
                .contentType(MediaType.parseMediaType(file.getMimeType()))
                .body(content);
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<FileVersionResponse>> getVersions(@PathVariable("id") Long id) {
        return ResponseEntity.ok(versionService.getFileVersions(id));
    }

    @GetMapping("/{id}/metadata")
    public ResponseEntity<List<FileMetadataResponse>> getMetadata(@PathVariable("id") Long id) {
        return ResponseEntity.ok(metadataService.getFileMetadata(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") Long id) {
        storageService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/restore")
    public ResponseEntity<StoredFileResponse> restoreFile(@PathVariable("id") Long id) {
        return ResponseEntity.ok(storageService.restoreFile(id));
    }

    @GetMapping("/quota")
    public ResponseEntity<FileQuotaResponse> getQuota(@RequestParam("workspaceId") String workspaceId) {
        return ResponseEntity.ok(quotaService.checkQuota(workspaceId));
    }
}
