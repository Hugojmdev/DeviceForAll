package com.hgo_soft.device_for_all.common.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotBlankIfPresentValidatorTest {

    private final NotBlankIfPresentValidator validator = new NotBlankIfPresentValidator();

    @Test
    void nullValueIsValid() {
        assertTrue(validator.isValid(null, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void blankValueIsInvalid(String value) {
        assertFalse(validator.isValid(value, null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Laptop", " Laptop ", "0"})
    void nonBlankValueIsValid(String value) {
        assertTrue(validator.isValid(value, null));
    }
}
