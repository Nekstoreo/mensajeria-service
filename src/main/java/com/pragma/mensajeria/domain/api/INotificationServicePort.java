package com.pragma.mensajeria.domain.api;

import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;

public interface INotificationServicePort {

    NotificationResult sendOrderReadyNotification(NotificationMessage message);
}
