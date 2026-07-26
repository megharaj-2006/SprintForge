package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.Markdown;

import java.util.regex.Pattern;

public class MarkdownValidator implements ConstraintValidator<Markdown, String> {

    private static final Pattern UNSAFE_PATTERN = Pattern.compile(
            "(?i)<script.*?>|(?i)</script.*?>|(?i)javascript:|(?i)\\bon\\w+\\s*=",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        // Safely allow markdown syntax but reject active scripts/XSS vectors
        return !UNSAFE_PATTERN.matcher(value).find();
    }
}
