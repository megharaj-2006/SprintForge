package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.NotPastDeadline;

import java.lang.reflect.Field;
import java.time.*;
import java.util.Date;

public class NotPastDeadlineValidator implements ConstraintValidator<NotPastDeadline, Object> {

    private String targetFieldName;
    private String deadlineFieldName;

    @Override
    public void initialize(NotPastDeadline constraintAnnotation) {
        this.targetFieldName = constraintAnnotation.targetField();
        this.deadlineFieldName = constraintAnnotation.deadlineField();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // If targetField and deadlineField are specified, treat as class-level comparison
        if (!targetFieldName.isEmpty() && !deadlineFieldName.isEmpty()) {
            try {
                Object targetObj = getFieldValue(value, targetFieldName);
                Object deadlineObj = getFieldValue(value, deadlineFieldName);

                if (targetObj == null || deadlineObj == null) {
                    return true;
                }

                if (targetObj instanceof Comparable && deadlineObj instanceof Comparable) {
                    Comparable<Object> target = (Comparable<Object>) targetObj;
                    Comparable<Object> deadline = (Comparable<Object>) deadlineObj;
                    // target <= deadline (not past deadline)
                    return target.compareTo(deadline) <= 0;
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }

        // Otherwise, treat as field-level comparison relative to "now" (deadline is now)
        if (value instanceof LocalDate localDate) {
            return !localDate.isBefore(LocalDate.now());
        }

        if (value instanceof LocalDateTime localDateTime) {
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

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = clazz.getSuperclass().getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        return field.get(object);
    }
}
