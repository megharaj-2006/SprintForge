package org.SprintForge.modules.user.validation;

import org.SprintForge.modules.user.exception.InvalidAvatarException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class AvatarFileValidator {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp"
    );

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidAvatarException("Avatar file must not be empty.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new InvalidAvatarException("Avatar file size must not exceed 5 MB.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new InvalidAvatarException("Invalid avatar file type. Allowed formats: JPEG, PNG, GIF, WEBP.");
        }
    }
}
