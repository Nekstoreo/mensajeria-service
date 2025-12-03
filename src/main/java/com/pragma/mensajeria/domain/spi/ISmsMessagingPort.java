package com.pragma.mensajeria.domain.spi;

import com.pragma.mensajeria.domain.model.NotificationResult;

public interface ISmsMessagingPort {

    NotificationResult sendSms(String phoneNumber, String message);
}
