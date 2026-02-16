package com.pragma.mensajeria.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class InvalidPhoneNumberExceptionTest {

    @Test
    void constructorWithMessage_ShouldSetMessage() {
        InvalidPhoneNumberException exception = new InvalidPhoneNumberException("invalid number");

        assertEquals("invalid number", exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_ShouldSetBothValues() {
        RuntimeException cause = new RuntimeException("root cause");
        InvalidPhoneNumberException exception = new InvalidPhoneNumberException("invalid number", cause);

        assertEquals("invalid number", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
