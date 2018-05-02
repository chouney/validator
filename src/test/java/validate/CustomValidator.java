// Copyright (C) 2017 Meituan
// All rights reserved
package validate;

import validate.annotation.Custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/6/9 PM1:48
 **/
public class CustomValidator implements ConstraintValidator<Custom,String> {
    @Override
    public void initialize(Custom custom) {

    }

    @Override
    public boolean isValid(String integer, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("this is new template").addConstraintViolation();
        return false;
    }
}