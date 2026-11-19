package com.hgo_soft.device_for_all.common.validators;

import com.hgo_soft.device_for_all.common.annotations.NotBlankIfPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        return value == null || !value.isBlank();
    }
}
