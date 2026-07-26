package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.NoDuplicateIdsValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoDuplicateIdsValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoDuplicateIds {
    String message() default "Collection must not contain items with duplicate IDs.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String idField() default "id";
}
