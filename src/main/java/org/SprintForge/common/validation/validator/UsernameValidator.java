package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.ValidUsername;

import java.util.Set;
import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_.-]{3,30}$";
    private static final Pattern PATTERN = Pattern.compile(USERNAME_PATTERN);

    private static final Set<String> RESERVED_USERNAMES = Set.of(
            "admin", "administrator", "root", "system", "superuser",
            "support", "help", "null", "undefined", "anonymous",
            "sprintforge", "api", "auth", "login", "register"
    );

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.trim().isEmpty()) {
            return true; // Allow null/empty if handled by @NotNull separately
        }

        String trimmed = username.trim().toLowerCase();

        if (RESERVED_USERNAMES.contains(trimmed)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username is a reserved word.")
                    .addConstraintViolation();
            return false;
        }

        return PATTERN.matcher(username).matches();
    }
}
