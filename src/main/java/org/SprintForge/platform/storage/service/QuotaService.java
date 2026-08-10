package org.SprintForge.platform.storage.service;

import lombok.RequiredArgsConstructor;
import org.SprintForge.platform.storage.dto.FileQuotaResponse;
import org.SprintForge.platform.storage.repository.PlatformStoredFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("platformQuotaService")
@RequiredArgsConstructor
public class QuotaService {

    private final PlatformStoredFileRepository storedFileRepository;

    @Value("${sprintforge.storage.max-workspace-quota-mb:10240}")
    private long maxWorkspaceQuotaMb;

    public FileQuotaResponse checkQuota(String workspaceId) {
        long usedBytes = storedFileRepository.calculateUsedSpaceByWorkspace(workspaceId);
        long maxBytes = maxWorkspaceQuotaMb * 1024 * 1024;
        double usagePct = maxBytes > 0 ? ((double) usedBytes / maxBytes) * 100.0 : 0.0;

        return FileQuotaResponse.builder()
                .workspaceId(workspaceId)
                .usedSpaceBytes(usedBytes)
                .maxQuotaBytes(maxBytes)
                .usagePercentage(usagePct)
                .isQuotaExceeded(usedBytes >= maxBytes)
                .build();
    }

    public void validateQuota(String workspaceId, long newFileSizeBytes) {
        FileQuotaResponse quota = checkQuota(workspaceId);
        if (quota.getUsedSpaceBytes() + newFileSizeBytes > quota.getMaxQuotaBytes()) {
            throw new IllegalStateException("Workspace storage quota exceeded (" + quota.getMaxQuotaBytes() + " bytes limit)");
        }
    }
}
