package com.cleverpy.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmGenreValidator.class)
@Target( { ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FilmGenre {
    String message() default "Invalid Film Genre";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
