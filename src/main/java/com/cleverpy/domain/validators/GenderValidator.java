package com.cleverpy.domain.validators;

import com.cleverpy.data.entities.GenderType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return GenderType.existsGender(value);
    }

}
