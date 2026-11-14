package com.pbl.back.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class ValidationTestSupport {

    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    protected void assertInvalid(Object value, String property, String messagePart) {
        assertFalse(VALIDATOR.validate(value).stream()
                .noneMatch(violation -> violation.getPropertyPath().toString().startsWith(property)
                        && violation.getMessage().contains(messagePart)),
                "Expected " + property + " violation containing: " + messagePart);
    }

    protected void assertValid(Object value) {
        assertTrue(VALIDATOR.validate(value).isEmpty(),
                () -> "Expected no violations but found " + VALIDATOR.validate(value));
    }
}
