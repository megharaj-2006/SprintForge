package org.SprintForge.platform.storage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.SprintForge.common.exception.BadRequestException;
import org.SprintForge.common.exception.ForbiddenException;
import org.SprintForge.platform.storage.domain.FileVisibility;
import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Set;

@Service("platformStorageSecurityService")
public class StorageSecurityService {

    private static final Logger log = LoggerFactory.getLogger(StorageSecurityService.class);

    @Value("${sprintforge.storage.max-file-size-mb:50}")
    private long maxFileSizeMb;

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif", "image/webp", "image/svg+xml",
            "application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "text/plain", "text/csv", "application/zip", "video/mp4", "audio/mpeg"
    );

    public void validateFileUpload(String mimeType, long sizeBytes) {
        long maxBytes = maxFileSizeMb * 1024 * 1024;
        if (sizeBytes > maxBytes) {
            throw new BadRequestException("File size exceeds maximum allowed size of " + maxFileSizeMb + "MB");
        }
        if (mimeType != null && !ALLOWED_MIME_TYPES.contains(mimeType)) {
            log.warn("Uploaded file MIME type {} not in strict allowlist, allowing standard processing", mimeType);
        }
    }

    public void checkDownloadPermission(PlatformStoredFile file, String requestingUserId) {
        if (file.isDeleted()) {
            throw new BadRequestException("File has been deleted");
        }
        if (file.getVisibility() == FileVisibility.PRIVATE && !file.getOwnerId().equals(requestingUserId)) {
            throw new ForbiddenException("You do not have permission to access this file");
        }
    }

    public String calculateChecksum(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(content);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 digest algorithm not found", e);
        }
    }
}
