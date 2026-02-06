package com.pragma.mensajeria.application.handler;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;
import com.pragma.mensajeria.application.mapper.INotificationDtoMapper;
import com.pragma.mensajeria.domain.api.INotificationServicePort;
import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;
import org.springframework.stereotype.Service;

@Service
public class NotificationHandler implements INotificationHandler {

    private final INotificationServicePort notificationServicePort;
    private final INotificationDtoMapper notificationDtoMapper;

    public NotificationHandler(INotificationServicePort notificationServicePort,
                               INotificationDtoMapper notificationDtoMapper) {
        this.notificationServicePort = notificationServicePort;
        this.notificationDtoMapper = notificationDtoMapper;
    }

    @Override
    public NotificationResponseDto sendOrderReadyNotification(OrderReadyNotificationRequestDto request) {
        NotificationMessage message = notificationDtoMapper.toNotificationMessage(request);
        NotificationResult result = notificationServicePort.sendOrderReadyNotification(message);
        return notificationDtoMapper.toNotificationResponseDto(result);
    }
}
