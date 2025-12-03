package com.pragma.mensajeria.infrastructure.output.twilio.adapter;

import com.pragma.mensajeria.domain.model.NotificationResult;
import com.pragma.mensajeria.domain.spi.ISmsMessagingPort;
import com.pragma.mensajeria.infrastructure.output.twilio.client.TwilioSmsClient;
import org.springframework.stereotype.Component;

@Component
public class TwilioSmsAdapter implements ISmsMessagingPort {

    private final TwilioSmsClient twilioSmsClient;

    public TwilioSmsAdapter(TwilioSmsClient twilioSmsClient) {
        this.twilioSmsClient = twilioSmsClient;
    }

    @Override
    public NotificationResult sendSms(String phoneNumber, String message) {
        return twilioSmsClient.sendSms(phoneNumber, message);
    }
}
