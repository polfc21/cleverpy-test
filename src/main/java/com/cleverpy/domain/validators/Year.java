package com.cleverpy.domain.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Year {
    String message() default "Invalid Year";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
