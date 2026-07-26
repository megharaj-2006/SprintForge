package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.MarkdownValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MarkdownValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Markdown {
    String message() default "Markdown text contains potentially unsafe elements (e.g. script tags).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
