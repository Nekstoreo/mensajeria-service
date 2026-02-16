package com.pragma.mensajeria.infrastructure.output.twilio.client;

import com.pragma.mensajeria.domain.model.NotificationResult;
import com.pragma.mensajeria.infrastructure.output.twilio.config.TwilioConfiguration;
import com.twilio.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TwilioSmsClientTest {

    @Mock
    private TwilioConfiguration twilioConfiguration;

    private TwilioSmsClient twilioSmsClient;

    @BeforeEach
    void setUp() {
        twilioSmsClient = new TwilioSmsClient(twilioConfiguration);
    }

    @Test
    void init_ShouldThrowWhenCredentialsAreMissing() {
        when(twilioConfiguration.getAccountSid()).thenReturn(null);
        when(twilioConfiguration.getAuthToken()).thenReturn(" ");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> twilioSmsClient.init());

        assertEquals("Twilio credentials are not configured", exception.getMessage());
    }

    @Test
    void init_ShouldInitializeWhenCredentialsArePresent() {
        when(twilioConfiguration.getAccountSid()).thenReturn("AC123");
        when(twilioConfiguration.getAuthToken()).thenReturn("token123");
        when(twilioConfiguration.getMessagingServiceSid()).thenReturn("MG123");

        assertDoesNotThrow(() -> twilioSmsClient.init());
    }

    @Test
    void sendSms_ShouldReturnFailureWhenPhoneNumberIsInvalid() {
        NotificationResult result = twilioSmsClient.sendSms("123-invalid", "message");

        assertFalse(result.isSuccess());
        assertNull(result.getMessageId());
        assertEquals("Invalid phone number format.", result.getErrorMessage());
        verify(twilioConfiguration, never()).getMessagingServiceSid();
    }

    @Test
    void sendSms_ShouldHandleApiException() {
        when(twilioConfiguration.getMessagingServiceSid()).thenThrow(new ApiException("twilio unavailable"));

        NotificationResult result = twilioSmsClient.sendSms("+573001234567", "message");

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains("Twilio API Error: twilio unavailable"));
    }

    @Test
    void sendSms_ShouldHandleUnexpectedException() {
        when(twilioConfiguration.getMessagingServiceSid()).thenThrow(new RuntimeException("unexpected"));

        NotificationResult result = twilioSmsClient.sendSms("+573001234567", "message");

        assertFalse(result.isSuccess());
        assertTrue(result.getErrorMessage().contains("Unexpected error sending SMS: unexpected"));
    }
}
