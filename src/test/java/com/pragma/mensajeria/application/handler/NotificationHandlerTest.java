package com.pragma.mensajeria.application.handler;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;
import com.pragma.mensajeria.application.mapper.NotificationDtoMapper;
import com.pragma.mensajeria.domain.api.INotificationServicePort;
import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationHandlerTest {

    @Mock
    private INotificationServicePort notificationServicePort;

    @Mock
    private NotificationDtoMapper notificationDtoMapper;

    @InjectMocks
    private NotificationHandler notificationHandler;

    @Test
    @DisplayName("Should send order ready notification successfully")
    void shouldSendOrderReadyNotificationSuccessfully() {
        OrderReadyNotificationRequestDto requestDto = new OrderReadyNotificationRequestDto(
                "+573001234567",
                "123",
                "456789",
                "Mi Restaurante"
        );

        NotificationMessage message = new NotificationMessage();
        message.setPhoneNumber("+573001234567");
        message.setOrderId("123");
        message.setSecurityPin("456789");
        message.setRestaurantName("Mi Restaurante");

        NotificationResult result = NotificationResult.success("MSG123");

        NotificationResponseDto expectedResponse = NotificationResponseDto.builder()
                .success(true)
                .messageId("MSG123")
                .message("Notification sent successfully")
                .build();

        when(notificationDtoMapper.toNotificationMessage(requestDto)).thenReturn(message);
        when(notificationServicePort.sendOrderReadyNotification(message)).thenReturn(result);
        when(notificationDtoMapper.toNotificationResponseDto(result)).thenReturn(expectedResponse);

        NotificationResponseDto response = notificationHandler.sendOrderReadyNotification(requestDto);

        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessageId()).isEqualTo("MSG123");

        verify(notificationDtoMapper).toNotificationMessage(requestDto);
        verify(notificationServicePort).sendOrderReadyNotification(message);
        verify(notificationDtoMapper).toNotificationResponseDto(result);
    }
}
