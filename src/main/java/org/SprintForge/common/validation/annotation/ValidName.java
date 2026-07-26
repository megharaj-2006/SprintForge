package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.ValidNameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidNameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Invalid name. Can only contain letters, spaces, hyphens, and apostrophes.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
