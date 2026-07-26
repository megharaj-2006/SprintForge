package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.NotPastDeadlineValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotPastDeadlineValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotPastDeadline {
    String message() default "Date must not be past the deadline.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    // For class-level validation: field names to check
    String targetField() default "";
    String deadlineField() default "";
}
