package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.NoScriptValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoScriptValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoScript {
    String message() default "Script tags or javascript URIs are not allowed.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
