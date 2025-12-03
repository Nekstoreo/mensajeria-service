package com.pragma.mensajeria.domain.usecase;

import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;
import com.pragma.mensajeria.domain.spi.ISmsMessagingPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationUseCaseTest {

    @Mock
    private ISmsMessagingPort smsMessagingPort;

    @InjectMocks
    private NotificationUseCase notificationUseCase;

    private static final String PHONE_NUMBER = "+573001234567";
    private static final String ORDER_ID = "123";
    private static final String SECURITY_PIN = "456789";
    private static final String RESTAURANT_NAME = "Mi Restaurante";

    @Nested
    @DisplayName("Send Order Ready Notification - Happy Path")
    class SendOrderReadyNotificationHappyPath {

        @Test
        @DisplayName("Should send notification successfully")
        void shouldSendNotificationSuccessfully() {
            NotificationMessage message = createValidNotificationMessage();
            NotificationResult expectedResult = NotificationResult.success("MSG123");

            when(smsMessagingPort.sendSms(eq(PHONE_NUMBER), anyString()))
                    .thenReturn(expectedResult);

            NotificationResult result = notificationUseCase.sendOrderReadyNotification(message);

            assertThat(result).isNotNull();
            assertThat(result.isSuccess()).isTrue();
            assertThat(result.getMessageId()).isEqualTo("MSG123");

            verify(smsMessagingPort).sendSms(eq(PHONE_NUMBER), anyString());
        }

        @Test
        @DisplayName("Should format message with order details")
        void shouldFormatMessageWithOrderDetails() {
            NotificationMessage message = createValidNotificationMessage();
            NotificationResult expectedResult = NotificationResult.success("MSG123");

            ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
            when(smsMessagingPort.sendSms(eq(PHONE_NUMBER), messageCaptor.capture()))
                    .thenReturn(expectedResult);

            notificationUseCase.sendOrderReadyNotification(message);

            String capturedMessage = messageCaptor.getValue();
            // Unificar múltiples aserciones en una sola cadena de AssertJ
            assertThat(capturedMessage)
                    .isNotNull()
                    .contains(ORDER_ID, SECURITY_PIN, RESTAURANT_NAME, "LISTO");
        }

        @Test
        @DisplayName("Should return failure result when messaging fails")
        void shouldReturnFailureResultWhenMessagingFails() {
            NotificationMessage message = createValidNotificationMessage();
            NotificationResult expectedResult = NotificationResult.failure("Failed to send message");

            when(smsMessagingPort.sendSms(eq(PHONE_NUMBER), anyString()))
                    .thenReturn(expectedResult);


            NotificationResult result = notificationUseCase.sendOrderReadyNotification(message);

            assertThat(result).isNotNull();
            assertThat(result.isSuccess()).isFalse();
            assertThat(result.getErrorMessage()).isEqualTo("Failed to send message");
        }
    }

    @Nested
    @DisplayName("Send Order Ready Notification - Validation")
    class SendOrderReadyNotificationValidation {

        @Test
        @DisplayName("Should throw exception when phone number is invalid or null")
        void shouldThrowExceptionWhenPhoneNumberIsInvalid() {
            NotificationMessage message = createValidNotificationMessage();
            message.setPhoneNumber(null);

            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Phone number is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }
    }

    private NotificationMessage createValidNotificationMessage() {
        return new NotificationMessage(
                PHONE_NUMBER,
                null,
                ORDER_ID,
                SECURITY_PIN,
                RESTAURANT_NAME
        );
    }
}
