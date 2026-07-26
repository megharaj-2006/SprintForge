package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.ValidCronValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidCronValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCron {
    String message() default "Invalid cron expression.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
