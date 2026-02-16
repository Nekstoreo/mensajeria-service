package com.pragma.mensajeria.infrastructure.output.twilio.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TwilioConfigurationTest {

    @Test
    void getters_ShouldReturnConfiguredValues() {
        TwilioConfiguration configuration = new TwilioConfiguration();

        ReflectionTestUtils.setField(configuration, "accountSid", "AC123");
        ReflectionTestUtils.setField(configuration, "authToken", "token123");
        ReflectionTestUtils.setField(configuration, "messagingServiceSid", "MG123");

        assertEquals("AC123", configuration.getAccountSid());
        assertEquals("token123", configuration.getAuthToken());
        assertEquals("MG123", configuration.getMessagingServiceSid());
    }
}
