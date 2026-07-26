package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.SlugValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SlugValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Slug {
    String message() default "Invalid slug format. Must be lowercase alphanumeric characters separated by single hyphens, with no leading or trailing hyphens.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
