package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.ValidName;

import java.util.regex.Pattern;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {

    // \p{L} is any kind of letter from any language.
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}'\\s-]+$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        if (value.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(value).matches();
    }
}
