package com.cleverpy.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target( { ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Gender {
    String message() default "Invalid Gender";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
