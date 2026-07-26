package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.MaxFileSizeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxFileSizeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxFileSize {
    String message() default "File size exceeds the allowed limit.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String value() default "5MB"; // e.g. "500KB", "10MB"
}
