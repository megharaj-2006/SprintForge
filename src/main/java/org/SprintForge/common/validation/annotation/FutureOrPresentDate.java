package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.FutureOrPresentDateValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureOrPresentDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrPresentDate {
    String message() default "Date must be today or in the future.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
