package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.TrimmedValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TrimmedValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Trimmed {
    String message() default "String must not have leading or trailing whitespace.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
