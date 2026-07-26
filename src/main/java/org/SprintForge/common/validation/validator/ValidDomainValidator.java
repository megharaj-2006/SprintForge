package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.ValidDomain;

import java.util.regex.Pattern;

public class ValidDomainValidator implements ConstraintValidator<ValidDomain, String> {

    // Matches standard domain names e.g. domain.com, sub.domain.org
    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
            "^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,18}$"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return DOMAIN_PATTERN.matcher(value).matches();
    }
}
