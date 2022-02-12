package com.cleverpy.domain.validators;

import com.cleverpy.data.entities.FilmGenreType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilmGenreValidator implements ConstraintValidator<FilmGenre, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return FilmGenreType.existsFilmGenre(value);
    }

}
