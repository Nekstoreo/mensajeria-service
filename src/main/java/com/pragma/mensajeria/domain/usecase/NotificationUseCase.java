package com.pragma.mensajeria.domain.usecase;

import com.pragma.mensajeria.domain.api.INotificationServicePort;
import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;
import com.pragma.mensajeria.domain.spi.ISmsMessagingPort;

public class NotificationUseCase implements INotificationServicePort {

    private static final String ORDER_READY_MESSAGE_TEMPLATE = 
            "¡Hola! Tu pedido #%s en %s está LISTO para recoger. " +
            "Tu PIN de seguridad es: %s. " +
            "Presenta este PIN al empleado para reclamar tu pedido.";

    private final ISmsMessagingPort smsMessagingPort;

    public NotificationUseCase(ISmsMessagingPort smsMessagingPort) {
        this.smsMessagingPort = smsMessagingPort;
    }

    @Override
    public NotificationResult sendOrderReadyNotification(NotificationMessage message) {
        validateNotificationMessage(message);

        String formattedMessage = String.format(
                ORDER_READY_MESSAGE_TEMPLATE,
                message.getOrderId(),
                message.getRestaurantName(),
                message.getSecurityPin()
        );

        return smsMessagingPort.sendSms(message.getPhoneNumber(), formattedMessage);
    }

    private void validateNotificationMessage(NotificationMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("Notification message cannot be null");
        }
        if (message.getPhoneNumber() == null || message.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required");
        }
        if (message.getSecurityPin() == null || message.getSecurityPin().trim().isEmpty()) {
            throw new IllegalArgumentException("Security PIN is required");
        }
        if (message.getOrderId() == null || message.getOrderId().trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID is required");
        }
        if (message.getRestaurantName() == null || message.getRestaurantName().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name is required");
        }
    }
}
