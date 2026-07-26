package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.StartBeforeEnd;

import java.lang.reflect.Field;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, Object> {

    private String startDateFieldName;
    private String endDateFieldName;

    @Override
    public void initialize(StartBeforeEnd constraintAnnotation) {
        this.startDateFieldName = constraintAnnotation.startDateField();
        this.endDateFieldName = constraintAnnotation.endDateField();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Object startDateObj = getFieldValue(value, startDateFieldName);
            Object endDateObj = getFieldValue(value, endDateFieldName);

            if (startDateObj == null || endDateObj == null) {
                return true; // Let @NotNull handle empty fields
            }

            if (startDateObj instanceof Comparable && endDateObj instanceof Comparable) {
                Comparable<Object> start = (Comparable<Object>) startDateObj;
                Comparable<Object> end = (Comparable<Object>) endDateObj;
                return start.compareTo(end) < 0;
            }

            return false; // Types are not comparable
        } catch (Exception e) {
            return false; // Reflection or comparison failed
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Check superclass
            field = clazz.getSuperclass().getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        return field.get(object);
    }
}
