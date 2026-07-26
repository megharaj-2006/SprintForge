package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.ImageOnlyValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageOnlyValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageOnly {
    String message() default "File must be an image.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
