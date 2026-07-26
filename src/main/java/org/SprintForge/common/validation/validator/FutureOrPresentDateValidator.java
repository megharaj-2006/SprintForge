package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.FutureOrPresentDate;

import java.time.*;
import java.util.Date;

public class FutureOrPresentDateValidator implements ConstraintValidator<FutureOrPresentDate, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value instanceof LocalDate localDate) {
            return !localDate.isBefore(LocalDate.now());
        }

        if (value instanceof LocalDateTime localDateTime) {
            // Compare at minute precision to avoid race conditions with milliseconds
            LocalDateTime now = LocalDateTime.now().minusSeconds(5);
            return !localDateTime.isBefore(now);
        }

        if (value instanceof Instant instant) {
            Instant now = Instant.now().minusSeconds(5);
            return !instant.isBefore(now);
        }

        if (value instanceof ZonedDateTime zonedDateTime) {
            ZonedDateTime now = ZonedDateTime.now().minusSeconds(5);
            return !zonedDateTime.isBefore(now);
        }

        if (value instanceof OffsetDateTime offsetDateTime) {
            OffsetDateTime now = OffsetDateTime.now().minusSeconds(5);
            return !offsetDateTime.isBefore(now);
        }

        if (value instanceof Date date) {
            Date now = new Date(System.currentTimeMillis() - 5000);
            return !date.before(now);
        }

        return false; // Unsupported type
    }
}
