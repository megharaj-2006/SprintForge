package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.UsernameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Invalid username format. Must be 3-30 characters long and contain only alphanumeric characters, underscores, dots, or hyphens.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
