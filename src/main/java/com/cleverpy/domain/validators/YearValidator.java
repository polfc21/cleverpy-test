package com.cleverpy.domain.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<Year, Integer> {

    private final static int YEAR_FIRST_MOVIE = 1895; //La sortie des ouvriers des usines Lumière à Lyon. Hermanos Lumière.
    private final static int ACTUAL_YEAR = 2022;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return YearValidator.YEAR_FIRST_MOVIE <= value && value <= YearValidator.ACTUAL_YEAR;
    }

}
