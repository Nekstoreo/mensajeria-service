package com.pragma.mensajeria.infrastructure.output.twilio.client;

import com.pragma.mensajeria.domain.model.NotificationResult;
import com.pragma.mensajeria.infrastructure.output.twilio.config.TwilioConfiguration;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwilioSmsClient {

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsClient.class);
    private static final String PHONE_NUMBER_PATTERN = "^\\+[1-9]\\d{1,14}$";

    private final TwilioConfiguration twilioConfiguration;

    public TwilioSmsClient(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @PostConstruct
    public void init() {
        Twilio.init(twilioConfiguration.getAccountSid(), twilioConfiguration.getAuthToken());
        logger.info("Twilio SMS client initialized successfully");
    }

    public NotificationResult sendSms(String toPhoneNumber, String messageBody) {
        try {
            // Validate phone number format
            if (!isValidPhoneNumber(toPhoneNumber)) {
                String errorMsg = "Invalid phone number format.";
                logger.warn("Validation error - {}: {}", errorMsg, toPhoneNumber);
                return NotificationResult.failure(errorMsg);
            }

            logger.info("Sending SMS to: {}", toPhoneNumber);
            logger.debug("Message Service SID: {}", twilioConfiguration.getMessagingServiceSid());

            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    twilioConfiguration.getMessagingServiceSid(),
                    messageBody
            ).create();

            logger.info("SMS sent successfully. SID: {}, Status: {}", message.getSid(), message.getStatus());
            return NotificationResult.success(message.getSid());

        } catch (ApiException e) {
            String errorMsg = String.format("Twilio API Error: %s", e.getMessage());
            logger.error(errorMsg, e);
            return NotificationResult.failure(errorMsg);
        } catch (Exception e) {
            String errorMsg = "Unexpected error sending SMS: " + e.getMessage();
            logger.error(errorMsg, e);
            return NotificationResult.failure(errorMsg);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}

