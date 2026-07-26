package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.MaxFileSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {

    private static final Pattern SIZE_PATTERN = Pattern.compile("^(\\d+)\\s*(KB|MB|GB)?$", Pattern.CASE_INSENSITIVE);
    private long maxSizeBytes;

    @Override
    public void initialize(MaxFileSize constraintAnnotation) {
        String sizeStr = constraintAnnotation.value().trim();
        Matcher matcher = SIZE_PATTERN.matcher(sizeStr);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid max file size format: " + sizeStr);
        }

        long sizeValue = Long.parseLong(matcher.group(1));
        String unit = matcher.group(2);

        if (unit == null) {
            this.maxSizeBytes = sizeValue; // Bytes
        } else {
            switch (unit.toUpperCase()) {
                case "KB":
                    this.maxSizeBytes = sizeValue * 1024;
                    break;
                case "MB":
                    this.maxSizeBytes = sizeValue * 1024 * 1024;
                    break;
                case "GB":
                    this.maxSizeBytes = sizeValue * 1024 * 1024 * 1024;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown unit: " + unit);
            }
        }
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true;
        }
        return file.getSize() <= maxSizeBytes;
    }
}
