package org.SprintForge.common.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.SprintForge.common.validation.annotation.NoDuplicateIds;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class NoDuplicateIdsValidator implements ConstraintValidator<NoDuplicateIds, Collection<?>> {

    private String idFieldName;

    @Override
    public void initialize(NoDuplicateIds constraintAnnotation) {
        this.idFieldName = constraintAnnotation.idField();
    }

    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        Set<Object> ids = new HashSet<>();
        for (Object item : value) {
            if (item == null) {
                continue;
            }
            try {
                Object id = getFieldValue(item, idFieldName);
                if (id != null) {
                    if (!ids.add(id)) {
                        return false; // Found duplicate ID!
                    }
                }
            } catch (Exception e) {
                return false; // Failed to read ID field
            }
        }
        return true;
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
