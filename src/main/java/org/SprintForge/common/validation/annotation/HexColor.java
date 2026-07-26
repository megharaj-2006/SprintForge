package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.HexColorValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HexColorValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HexColor {
    String message() default "Invalid Hex Color code. Must start with # followed by 3 or 6 hexadecimal characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
