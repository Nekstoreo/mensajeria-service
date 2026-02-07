package com.pragma.mensajeria.infrastructure.input.rest.controller;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;
import com.pragma.mensajeria.application.handler.INotificationHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Notification Rest Controller Tests")
class NotificationRestControllerTest {

    private static final String VALID_PHONE = "+573001234567";
    private static final String VALID_ORDER_ID = "123";
    private static final String VALID_SECURITY_PIN = "456789";
    private static final String VALID_RESTAURANT_NAME = "Mi Restaurante";
    private static final String SUCCESS_MESSAGE_ID = "MSG123";
    private static final String SUCCESS_MESSAGE_TEXT = "Notification sent successfully";
    private static final String ERROR_MESSAGE = "Failed to send notification";

    @Mock
    private INotificationHandler notificationHandler;

    @InjectMocks
    private NotificationRestController notificationRestController;

    @Test
    @DisplayName("Should send order ready notification successfully with 200 status")
    void shouldSendOrderReadyNotificationSuccessfully() {
        // Arrange
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto successResponse = NotificationResponseDto.builder()
                .success(true)
                .messageId(SUCCESS_MESSAGE_ID)
                .message(SUCCESS_MESSAGE_TEXT)
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(successResponse);

        // Act
        ResponseEntity<NotificationResponseDto> response = notificationRestController
                .sendOrderReadyNotification(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
        assertThat(response.getBody().getMessage()).isEqualTo(SUCCESS_MESSAGE_TEXT);

        verify(notificationHandler).sendOrderReadyNotification(request);
    }

    @Test
    @DisplayName("Should return 500 status when notification sending fails")
    void shouldReturnServerErrorWhenNotificationSendingFails() {
        // Arrange
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto failureResponse = NotificationResponseDto.builder()
                .success(false)
                .message(ERROR_MESSAGE)
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(failureResponse);

        // Act
        ResponseEntity<NotificationResponseDto> response = notificationRestController
                .sendOrderReadyNotification(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isSuccess()).isFalse();
        assertThat(response.getBody().getMessage()).isEqualTo(ERROR_MESSAGE);

        verify(notificationHandler).sendOrderReadyNotification(request);
    }

    @Test
    @DisplayName("Should call handler with correct request data")
    void shouldCallHandlerWithCorrectRequestData() {
        // Arrange
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto response = NotificationResponseDto.builder()
                .success(true)
                .messageId(SUCCESS_MESSAGE_ID)
                .message(SUCCESS_MESSAGE_TEXT)
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(response);

        // Act
        notificationRestController.sendOrderReadyNotification(request);

        // Assert
        verify(notificationHandler).sendOrderReadyNotification(request);
    }

    @Test
    @DisplayName("Should handle notification with different phone numbers")
    void shouldHandleNotificationWithDifferentPhoneNumbers() {
        // Arrange
        String anotherPhone = "+573009876543";
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                anotherPhone,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto response = NotificationResponseDto.builder()
                .success(true)
                .messageId(SUCCESS_MESSAGE_ID)
                .message(SUCCESS_MESSAGE_TEXT)
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(response);

        // Act
        ResponseEntity<NotificationResponseDto> result = notificationRestController
                .sendOrderReadyNotification(request);

        // Assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(notificationHandler).sendOrderReadyNotification(request);
    }

    @Test
    @DisplayName("Should handle notification with different message IDs")
    void shouldHandleNotificationWithDifferentMessageIds() {
        // Arrange
        String customMessageId = "CUSTOM_MSG_456";
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto response = NotificationResponseDto.builder()
                .success(true)
                .messageId(customMessageId)
                .message(SUCCESS_MESSAGE_TEXT)
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(response);

        // Act
        ResponseEntity<NotificationResponseDto> result = notificationRestController
                .sendOrderReadyNotification(request);

        // Assert
        assertThat(result.getBody().getMessageId()).isEqualTo(customMessageId);
    }

    @Test
    @DisplayName("Should instantiate controller with INotificationHandler dependency")
    void shouldInstantiateControllerWithDependency() {
        // Assert
        assertThat(notificationRestController).isNotNull();
    }

    @Test
    @DisplayName("Should handle success response with all fields populated")
    void shouldHandleSuccessResponseWithAllFieldsPopulated() {
        // Arrange
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto response = NotificationResponseDto.builder()
                .success(true)
                .messageId(SUCCESS_MESSAGE_ID)
                .message(SUCCESS_MESSAGE_TEXT)
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(response);

        // Act
        ResponseEntity<NotificationResponseDto> result = notificationRestController
                .sendOrderReadyNotification(request);

        // Assert
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().isSuccess()).isTrue();
        assertThat(result.getBody().getMessageId()).isNotNull();
        assertThat(result.getBody().getMessage()).isNotNull();
    }

    @Test
    @DisplayName("Should preserve response body content with success response")
    void shouldPreserveResponseBodyContentWithSuccessResponse() {
        // Arrange
        OrderReadyNotificationRequestDto request = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        NotificationResponseDto originalResponse = NotificationResponseDto.builder()
                .success(true)
                .messageId("ORIGINAL_ID")
                .message("Original message")
                .build();

        when(notificationHandler.sendOrderReadyNotification(request)).thenReturn(originalResponse);

        // Act
        ResponseEntity<NotificationResponseDto> result = notificationRestController
                .sendOrderReadyNotification(request);

        // Assert
        assertThat(result.getBody().getMessageId()).isEqualTo("ORIGINAL_ID");
        assertThat(result.getBody().getMessage()).isEqualTo("Original message");
    }
}
