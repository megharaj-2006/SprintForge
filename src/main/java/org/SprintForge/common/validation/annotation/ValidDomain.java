package org.SprintForge.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.SprintForge.common.validation.validator.ValidDomainValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDomainValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDomain {
    String message() default "Invalid domain name format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
