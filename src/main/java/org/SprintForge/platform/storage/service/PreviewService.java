package org.SprintForge.platform.storage.service;

import lombok.extern.slf4j.Slf4j;
import org.SprintForge.platform.storage.domain.PlatformStoredFile;
import org.springframework.stereotype.Service;

@Slf4j
@Service("platformPreviewService")
public class PreviewService {

    public String generatePreviewUrl(PlatformStoredFile file) {
        if (file.getMimeType() != null && file.getMimeType().startsWith("image/")) {
            return "/api/v1/files/" + file.getId() + "/preview?type=image";
        } else if ("application/pdf".equals(file.getMimeType())) {
            return "/api/v1/files/" + file.getId() + "/preview?type=pdf";
        }
        return "/api/v1/files/" + file.getId() + "/download";
    }

    public boolean supportsThumbnail(String mimeType) {
        return mimeType != null && (mimeType.startsWith("image/") || "application/pdf".equals(mimeType));
    }
}
