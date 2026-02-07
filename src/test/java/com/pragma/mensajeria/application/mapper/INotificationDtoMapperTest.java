package com.pragma.mensajeria.application.mapper;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;
import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("Notification DTO Mapper Tests")
class INotificationDtoMapperTest {

    private static final String VALID_PHONE = "+573001234567";
    private static final String VALID_ORDER_ID = "123";
    private static final String VALID_SECURITY_PIN = "456789";
    private static final String VALID_RESTAURANT_NAME = "Mi Restaurante";
    private static final String SUCCESS_MESSAGE_ID = "MSG123";
    private static final String ERROR_MESSAGE = "Failed to send message";

    @Autowired
    private INotificationDtoMapper notificationDtoMapper;

    @Test
    @DisplayName("Should map OrderReadyNotificationRequestDto to NotificationMessage")
    void shouldMapOrderReadyNotificationRequestDtoToNotificationMessage() {
        // Arrange
        OrderReadyNotificationRequestDto requestDto = new OrderReadyNotificationRequestDto(
                VALID_PHONE,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        // Act
        NotificationMessage result = notificationDtoMapper.toNotificationMessage(requestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPhoneNumber()).isEqualTo(VALID_PHONE);
        assertThat(result.getOrderId()).isEqualTo(VALID_ORDER_ID);
        assertThat(result.getSecurityPin()).isEqualTo(VALID_SECURITY_PIN);
        assertThat(result.getRestaurantName()).isEqualTo(VALID_RESTAURANT_NAME);
    }

    @Test
    @DisplayName("Should map NotificationResult with success to NotificationResponseDto")
    void shouldMapNotificationResultWithSuccessToNotificationResponseDto() {
        // Arrange
        NotificationResult result = NotificationResult.success(SUCCESS_MESSAGE_ID);

        // Act
        NotificationResponseDto response = notificationDtoMapper.toNotificationResponseDto(result);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
        assertThat(response.getMessage()).isEqualTo("Notification sent successfully");
    }

    @Test
    @DisplayName("Should map NotificationResult with failure to NotificationResponseDto")
    void shouldMapNotificationResultWithFailureToNotificationResponseDto() {
        // Arrange
        NotificationResult result = NotificationResult.failure(ERROR_MESSAGE);

        // Act
        NotificationResponseDto response = notificationDtoMapper.toNotificationResponseDto(result);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    @DisplayName("Should preserve all fields when mapping OrderReadyNotificationRequestDto")
    void shouldPreserveAllFieldsWhenMappingOrderReadyNotificationRequestDto() {
        // Arrange
        String customPhone = "+573119876543";
        String customOrderId = "ORDER_999";
        String customPin = "123456";
        String customRestaurant = "Gourmet Restaurant";

        OrderReadyNotificationRequestDto requestDto = new OrderReadyNotificationRequestDto(
                customPhone,
                customOrderId,
                customPin,
                customRestaurant
        );

        // Act
        NotificationMessage result = notificationDtoMapper.toNotificationMessage(requestDto);

        // Assert
        assertThat(result.getPhoneNumber()).isEqualTo(customPhone);
        assertThat(result.getOrderId()).isEqualTo(customOrderId);
        assertThat(result.getSecurityPin()).isEqualTo(customPin);
        assertThat(result.getRestaurantName()).isEqualTo(customRestaurant);
    }

    @Test
    @DisplayName("Should handle different success message IDs in NotificationResult")
    void shouldHandleDifferentSuccessMessageIdsInNotificationResult() {
        // Arrange
        String customMessageId = "CUSTOM_MSG_789";
        NotificationResult result = NotificationResult.success(customMessageId);

        // Act
        NotificationResponseDto response = notificationDtoMapper.toNotificationResponseDto(result);

        // Assert
        assertThat(response.getMessageId()).isEqualTo(customMessageId);
        assertThat(response.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("Should include success message when mapping successful NotificationResult")
    void shouldIncludeSuccessMessageWhenMappingSuccessfulNotificationResult() {
        // Arrange
        NotificationResult result = NotificationResult.success("TEST_ID");

        // Act
        NotificationResponseDto response = notificationDtoMapper.toNotificationResponseDto(result);

        // Assert
        assertThat(response.getMessage()).isNotEmpty();
        assertThat(response.getMessage()).isEqualTo("Notification sent successfully");
    }

    @Test
    @DisplayName("Should include error message when mapping failed NotificationResult")
    void shouldIncludeErrorMessageWhenMappingFailedNotificationResult() {
        // Arrange
        String customErrorMessage = "Twilio service unavailable";
        NotificationResult result = NotificationResult.failure(customErrorMessage);

        // Act
        NotificationResponseDto response = notificationDtoMapper.toNotificationResponseDto(result);

        // Assert
        assertThat(response.getMessage()).isEqualTo(customErrorMessage);
        assertThat(response.isSuccess()).isFalse();
    }

    @Test
    @DisplayName("Should map request with whitespace in fields correctly")
    void shouldMapRequestWithWhitespaceInFieldsCorrectly() {
        // Arrange
        OrderReadyNotificationRequestDto requestDto = new OrderReadyNotificationRequestDto(
                "  +573001234567  ",
                "  123  ",
                "  456789  ",
                "  Mi Restaurante  "
        );

        // Act
        NotificationMessage result = notificationDtoMapper.toNotificationMessage(requestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getPhoneNumber()).isEqualTo("  +573001234567  ");
        assertThat(result.getOrderId()).isEqualTo("  123  ");
    }

    @Test
    @DisplayName("Should map mapper is not null")
    void shouldMapperIsNotNull() {
        // Assert
        assertThat(notificationDtoMapper).isNotNull();
    }
}
