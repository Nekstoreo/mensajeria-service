package com.pragma.mensajeria.infrastructure.constant;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void apiConstants_ShouldExposeExpectedValues() {
        assertEquals("/api/v1", ApiConstants.API_VERSION);
        assertEquals("/api/v1/notifications", ApiConstants.NOTIFICATIONS_BASE_PATH);
        assertEquals("application/json", ApiConstants.APPLICATION_JSON);
        assertEquals("ORDER_READY", ApiConstants.NOTIFICATION_ORDER_READY);
    }

    @Test
    void securityConstants_ShouldExposeExpectedValues() {
        assertEquals("Bearer ", SecurityConstants.BEARER_PREFIX);
        assertEquals("Authorization", SecurityConstants.JWT_HEADER);
        assertEquals("JWT", SecurityConstants.JWT_TOKEN_TYPE);
        assertEquals("ROLE_", SecurityConstants.ROLE_PREFIX);
    }

    @Test
    void validationConstants_ShouldExposeExpectedValues() {
        assertTrue(ValidationConstants.PHONE_PATTERN.matcher("+573001234567").matches());
        assertFalse(ValidationConstants.PHONE_PATTERN.matcher("abc").matches());
        assertEquals(15, ValidationConstants.MAX_PHONE_LENGTH);
        assertEquals(7, ValidationConstants.MIN_PHONE_LENGTH);
        assertEquals(160, ValidationConstants.MAX_MESSAGE_LENGTH);
    }

    @Test
    void utilityClasses_ConstructorsShouldThrowAssertionError() throws Exception {
        assertConstructorThrows(ApiConstants.class, "Cannot instantiate ApiConstants");
        assertConstructorThrows(SecurityConstants.class, "Cannot instantiate SecurityConstants");
        assertConstructorThrows(ValidationConstants.class, "Cannot instantiate ValidationConstants");
    }

    private void assertConstructorThrows(Class<?> utilityClass, String message) throws Exception {
        Constructor<?> constructor = utilityClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getTargetException() instanceof AssertionError);
        assertEquals(message, exception.getTargetException().getMessage());
    }
}
