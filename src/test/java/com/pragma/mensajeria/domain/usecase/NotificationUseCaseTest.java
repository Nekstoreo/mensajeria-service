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
                    .contains(ORDER_ID, SECURITY_PIN, RESTAURANT_NAME, "READY");
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

        @Test
        @DisplayName("Should throw exception when phone number is empty")
        void shouldThrowExceptionWhenPhoneNumberIsEmpty() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setPhoneNumber("");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Phone number is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when phone number is only whitespace")
        void shouldThrowExceptionWhenPhoneNumberIsOnlyWhitespace() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setPhoneNumber("   ");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Phone number is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when notification message is null")
        void shouldThrowExceptionWhenNotificationMessageIsNull() {
            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Notification message cannot be null");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when security PIN is null")
        void shouldThrowExceptionWhenSecurityPinIsNull() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setSecurityPin(null);

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Security PIN is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when security PIN is empty")
        void shouldThrowExceptionWhenSecurityPinIsEmpty() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setSecurityPin("");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Security PIN is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when security PIN is only whitespace")
        void shouldThrowExceptionWhenSecurityPinIsOnlyWhitespace() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setSecurityPin("   ");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Security PIN is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when order ID is null")
        void shouldThrowExceptionWhenOrderIdIsNull() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setOrderId(null);

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Order ID is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when order ID is empty")
        void shouldThrowExceptionWhenOrderIdIsEmpty() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setOrderId("");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Order ID is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when order ID is only whitespace")
        void shouldThrowExceptionWhenOrderIdIsOnlyWhitespace() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setOrderId("   ");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Order ID is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when restaurant name is null")
        void shouldThrowExceptionWhenRestaurantNameIsNull() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setRestaurantName(null);

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Restaurant name is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when restaurant name is empty")
        void shouldThrowExceptionWhenRestaurantNameIsEmpty() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setRestaurantName("");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Restaurant name is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should throw exception when restaurant name is only whitespace")
        void shouldThrowExceptionWhenRestaurantNameIsOnlyWhitespace() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            message.setRestaurantName("   ");

            // Act & Assert
            assertThatThrownBy(() -> notificationUseCase.sendOrderReadyNotification(message))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Restaurant name is required");

            verify(smsMessagingPort, never()).sendSms(anyString(), anyString());
        }

        @Test
        @DisplayName("Should format message with template when all validations pass")
        void shouldFormatMessageWithTemplate() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            NotificationResult expectedResult = NotificationResult.success("MSG456");
            ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

            when(smsMessagingPort.sendSms(eq(PHONE_NUMBER), messageCaptor.capture()))
                    .thenReturn(expectedResult);

            // Act
            NotificationResult result = notificationUseCase.sendOrderReadyNotification(message);

            // Assert
            String capturedMessage = messageCaptor.getValue();
            assertThat(capturedMessage)
                    .contains(ORDER_ID)
                    .contains(RESTAURANT_NAME)
                    .contains(SECURITY_PIN)
                    .contains("READY")
                    .contains("PIN");

            assertThat(result.isSuccess()).isTrue();
        }

        @Test
        @DisplayName("Should call SMS port with correct phone and formatted message")
        void shouldCallSmsPortWithCorrectPhoneAndFormattedMessage() {
            // Arrange
            NotificationMessage message = createValidNotificationMessage();
            NotificationResult expectedResult = NotificationResult.success("MSG789");
            ArgumentCaptor<String> phoneCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

            when(smsMessagingPort.sendSms(phoneCaptor.capture(), messageCaptor.capture()))
                    .thenReturn(expectedResult);

            // Act
            notificationUseCase.sendOrderReadyNotification(message);

            // Assert
            assertThat(phoneCaptor.getValue()).isEqualTo(PHONE_NUMBER);
            assertThat(messageCaptor.getValue()).isNotEmpty();

            verify(smsMessagingPort).sendSms(PHONE_NUMBER, messageCaptor.getValue());
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
