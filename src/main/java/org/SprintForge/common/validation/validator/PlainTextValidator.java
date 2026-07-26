package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.PlainText;

import java.util.regex.Pattern;

public class PlainTextValidator implements ConstraintValidator<PlainText, String> {

    // Matches HTML tags, markdown bold/italic (*, _), code (`), links/images ([, ]), or headers (^# )
    private static final Pattern FORMATTING_PATTERN = Pattern.compile(
            "(<[^>]+>|[*_`\\[\\]~]|^#+\\s+)",
            Pattern.MULTILINE
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return !FORMATTING_PATTERN.matcher(value).find();
    }
}
