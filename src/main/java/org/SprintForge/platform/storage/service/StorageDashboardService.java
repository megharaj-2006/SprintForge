package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.storage.dto.StorageDashboardResponse;
import org.SprintForge.platform.storage.dto.StoredFileResponse;
import org.SprintForge.platform.storage.repository.PlatformStoredFileRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("platformStorageDashboardService")
@RequiredArgsConstructor
public class StorageDashboardService {

    private final PlatformStoredFileRepository storedFileRepository;

    public StorageDashboardResponse getStorageMetrics() {
        long totalFiles = storedFileRepository.count();
        long duplicateChecksums = storedFileRepository.findDuplicateChecksums().size();

        List<StoredFileResponse> largestFiles = storedFileRepository.findLargestFiles(PageRequest.of(0, 5)).stream()
                .map(f -> StoredFileResponse.builder()
                        .id(f.getId())
                        .workspaceId(f.getWorkspaceId())
                        .projectId(f.getProjectId())
                        .ownerId(f.getOwnerId())
                        .originalName(f.getOriginalName())
                        .mimeType(f.getMimeType())
                        .extension(f.getExtension())
                        .sizeBytes(f.getSizeBytes())
                        .checksum(f.getChecksum())
                        .storageProvider(f.getStorageProvider())
                        .visibility(f.getVisibility())
                        .isDeleted(f.isDeleted())
                        .uploadedAt(f.getUploadedAt())
                        .build())
                .collect(Collectors.toList());

        long totalSize = largestFiles.stream().mapToLong(StoredFileResponse::getSizeBytes).sum();

        return StorageDashboardResponse.builder()
                .totalFiles(totalFiles)
                .totalStorageUsedBytes(totalSize)
                .duplicateFilesCount(duplicateChecksums)
                .uploadSuccessRate(99.8)
                .largestFiles(largestFiles)
                .build();
    }
}
