package com.pragma.mensajeria.infrastructure.output.twilio.adapter;

import com.pragma.mensajeria.domain.model.NotificationResult;
import com.pragma.mensajeria.infrastructure.output.twilio.client.TwilioSmsClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TwilioSmsAdapterTest {

    @Mock
    private TwilioSmsClient twilioSmsClient;

    @InjectMocks
    private TwilioSmsAdapter twilioSmsAdapter;

    @Test
    void sendSms_ShouldDelegateToClient() {
        NotificationResult expected = NotificationResult.success("MSG123");
        when(twilioSmsClient.sendSms("+573001234567", "hello")).thenReturn(expected);

        NotificationResult result = twilioSmsAdapter.sendSms("+573001234567", "hello");

        assertTrue(result.isSuccess());
        assertEquals("MSG123", result.getMessageId());
        verify(twilioSmsClient).sendSms("+573001234567", "hello");
    }
}
