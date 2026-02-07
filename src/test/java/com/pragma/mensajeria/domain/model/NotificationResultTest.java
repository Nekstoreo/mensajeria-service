package com.pragma.mensajeria.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Notification Result Tests")
class NotificationResultTest {

    private static final String SUCCESS_MESSAGE_ID = "MSG123";
    private static final String ERROR_MESSAGE = "Failed to send notification";
    private static final String ALTERNATIVE_MESSAGE_ID = "MSG456";

    @Test
    @DisplayName("Should create NotificationResult with no-arg constructor")
    void shouldCreateNotificationResultWithNoArgConstructor() {
        // Act
        NotificationResult result = new NotificationResult();

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should create NotificationResult with all-arg constructor")
    void shouldCreateNotificationResultWithAllArgConstructor() {
        // Act
        NotificationResult result = new NotificationResult(true, SUCCESS_MESSAGE_ID, null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("Should create successful NotificationResult using factory method")
    void shouldCreateSuccessfulNotificationResultUsingFactoryMethod() {
        // Act
        NotificationResult result = NotificationResult.success(SUCCESS_MESSAGE_ID);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("Should create failed NotificationResult using factory method")
    void shouldCreateFailedNotificationResultUsingFactoryMethod() {
        // Act
        NotificationResult result = NotificationResult.failure(ERROR_MESSAGE);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessageId()).isNull();
        assertThat(result.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    @DisplayName("Should set and get success status")
    void shouldSetAndGetSuccessStatus() {
        // Arrange
        NotificationResult result = new NotificationResult();

        // Act
        result.setSuccess(true);

        // Assert
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("Should set and get message ID")
    void shouldSetAndGetMessageId() {
        // Arrange
        NotificationResult result = new NotificationResult();

        // Act
        result.setMessageId(SUCCESS_MESSAGE_ID);

        // Assert
        assertThat(result.getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
    }

    @Test
    @DisplayName("Should set and get error message")
    void shouldSetAndGetErrorMessage() {
        // Arrange
        NotificationResult result = new NotificationResult();

        // Act
        result.setErrorMessage(ERROR_MESSAGE);

        // Assert
        assertThat(result.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
    }

    @Test
    @DisplayName("Should handle success result with different message IDs")
    void shouldHandleSuccessResultWithDifferentMessageIds() {
        // Act
        NotificationResult result1 = NotificationResult.success(SUCCESS_MESSAGE_ID);
        NotificationResult result2 = NotificationResult.success(ALTERNATIVE_MESSAGE_ID);

        // Assert
        assertThat(result1.getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
        assertThat(result2.getMessageId()).isEqualTo(ALTERNATIVE_MESSAGE_ID);
        assertThat(result1.isSuccess()).isTrue();
        assertThat(result2.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("Should handle failure result with different error messages")
    void shouldHandleFailureResultWithDifferentErrorMessages() {
        // Arrange
        String error1 = "Network error";
        String error2 = "Twilio service unavailable";

        // Act
        NotificationResult result1 = NotificationResult.failure(error1);
        NotificationResult result2 = NotificationResult.failure(error2);

        // Assert
        assertThat(result1.getErrorMessage()).isEqualTo(error1);
        assertThat(result2.getErrorMessage()).isEqualTo(error2);
        assertThat(result1.isSuccess()).isFalse();
        assertThat(result2.isSuccess()).isFalse();
    }

    @Test
    @DisplayName("Should update success status from false to true")
    void shouldUpdateSuccessStatusFromFalseToTrue() {
        // Arrange
        NotificationResult result = new NotificationResult();
        result.setSuccess(false);

        // Act
        result.setSuccess(true);

        // Assert
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("Should update success status from true to false")
    void shouldUpdateSuccessStatusFromTrueToFalse() {
        // Arrange
        NotificationResult result = new NotificationResult();
        result.setSuccess(true);

        // Act
        result.setSuccess(false);

        // Assert
        assertThat(result.isSuccess()).isFalse();
    }

    @Test
    @DisplayName("Should handle null message ID")
    void shouldHandleNullMessageId() {
        // Arrange
        NotificationResult result = new NotificationResult();

        // Act
        result.setMessageId(null);

        // Assert
        assertThat(result.getMessageId()).isNull();
    }

    @Test
    @DisplayName("Should handle null error message")
    void shouldHandleNullErrorMessage() {
        // Arrange
        NotificationResult result = new NotificationResult();

        // Act
        result.setErrorMessage(null);

        // Assert
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("Should create multiple instances independently with success factory method")
    void shouldCreateMultipleInstancesIndependentlyWithSuccessFactory() {
        // Act
        NotificationResult result1 = NotificationResult.success("ID1");
        NotificationResult result2 = NotificationResult.success("ID2");

        // Assert
        assertThat(result1.getMessageId()).isEqualTo("ID1");
        assertThat(result2.getMessageId()).isEqualTo("ID2");
        assertThat(result1).isNotSameAs(result2);
    }

    @Test
    @DisplayName("Should create multiple instances independently with failure factory method")
    void shouldCreateMultipleInstancesIndependentlyWithFailureFactory() {
        // Act
        NotificationResult result1 = NotificationResult.failure("Error 1");
        NotificationResult result2 = NotificationResult.failure("Error 2");

        // Assert
        assertThat(result1.getErrorMessage()).isEqualTo("Error 1");
        assertThat(result2.getErrorMessage()).isEqualTo("Error 2");
        assertThat(result1).isNotSameAs(result2);
    }

    @Test
    @DisplayName("Should preserve success state when updating message ID")
    void shouldPreserveSuccessStateWhenUpdatingMessageId() {
        // Arrange
        NotificationResult result = new NotificationResult(true, "OLD_ID", null);

        // Act
        result.setMessageId("NEW_ID");

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessageId()).isEqualTo("NEW_ID");
    }

    @Test
    @DisplayName("Should preserve failure state when updating error message")
    void shouldPreserveFailureStateWhenUpdatingErrorMessage() {
        // Arrange
        NotificationResult result = new NotificationResult(false, null, "Old error");

        // Act
        result.setErrorMessage("New error");

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("New error");
    }

    @Test
    @DisplayName("Should handle all-arg constructor with success values")
    void shouldHandleAllArgConstructorWithSuccessValues() {
        // Act
        NotificationResult result = new NotificationResult(true, SUCCESS_MESSAGE_ID, null);

        // Assert
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessageId()).isEqualTo(SUCCESS_MESSAGE_ID);
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("Should handle all-arg constructor with failure values")
    void shouldHandleAllArgConstructorWithFailureValues() {
        // Act
        NotificationResult result = new NotificationResult(false, null, ERROR_MESSAGE);

        // Assert
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getMessageId()).isNull();
        assertThat(result.getErrorMessage()).isEqualTo(ERROR_MESSAGE);
    }
}
