package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.PlainTextValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlainTextValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlainText {
    String message() default "Text must not contain markdown styling or HTML formatting.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
