package com.cleverpy.validators;

import com.cleverpy.entities.GenderType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<Gender, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return GenderType.existsGender(value);
    }

}
