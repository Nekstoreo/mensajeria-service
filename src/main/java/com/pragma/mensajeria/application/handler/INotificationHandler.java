package com.pragma.mensajeria.application.handler;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;

public interface INotificationHandler {

    NotificationResponseDto sendOrderReadyNotification(OrderReadyNotificationRequestDto request);
}
