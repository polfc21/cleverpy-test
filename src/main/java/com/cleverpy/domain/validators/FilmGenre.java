package com.cleverpy.domain.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmGenreValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FilmGenre {
    String message() default "Invalid Film Genre";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
