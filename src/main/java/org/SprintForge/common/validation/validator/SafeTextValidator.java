package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.SafeText;

import java.util.regex.Pattern;

public class SafeTextValidator implements ConstraintValidator<SafeText, String> {

    private static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>");
    private static final Pattern SCRIPT_PATTERN = Pattern.compile(
            "(?i)<script.*?>|(?i)</script.*?>|(?i)javascript:|(?i)\\bon\\w+\\s*=",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return !HTML_PATTERN.matcher(value).find() && !SCRIPT_PATTERN.matcher(value).find();
    }
}
