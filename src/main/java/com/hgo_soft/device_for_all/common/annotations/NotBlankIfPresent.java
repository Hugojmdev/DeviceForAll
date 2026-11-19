package com.hgo_soft.device_for_all.common.annotations;

import com.hgo_soft.device_for_all.common.validators.NotBlankIfPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Documented
public @interface NotBlankIfPresent {
    String message() default "Must not be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
