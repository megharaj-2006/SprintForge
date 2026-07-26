package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.AllowedFileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class AllowedFileTypeValidator implements ConstraintValidator<AllowedFileType, MultipartFile> {

    private String[] allowedTypes;

    @Override
    public void initialize(AllowedFileType constraintAnnotation) {
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Null or empty is considered valid (use @NotNull/@NotEmpty separately)
        }

        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();

        if (allowedTypes == null || allowedTypes.length == 0) {
            return true;
        }

        return Arrays.stream(allowedTypes).anyMatch(allowed -> {
            if (allowed.startsWith(".")) {
                // Extension check
                return filename != null && filename.toLowerCase().endsWith(allowed.toLowerCase());
            } else if (allowed.contains("/")) {
                // MIME type check
                return contentType != null && contentType.equalsIgnoreCase(allowed);
            }
            return false;
        });
    }
}
