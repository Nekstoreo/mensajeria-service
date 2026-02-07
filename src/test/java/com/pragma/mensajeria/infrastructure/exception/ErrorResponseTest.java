package com.pragma.mensajeria.infrastructure.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Error Response Tests")
class ErrorResponseTest {

    private static final LocalDateTime TEST_TIMESTAMP = LocalDateTime.of(2026, 2, 6, 17, 54, 57);
    private static final int TEST_STATUS = 400;
    private static final String TEST_ERROR = "Bad Request";
    private static final String TEST_MESSAGE = "Invalid input data";
    private static final String TEST_PATH = "/api/v1/notifications";

    @Test
    @DisplayName("Should create ErrorResponse with no-arg constructor")
    void shouldCreateErrorResponseWithNoArgConstructor() {
        // Act
        ErrorResponse response = new ErrorResponse();

        // Assert
        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("Should create ErrorResponse with all-arg constructor")
    void shouldCreateErrorResponseWithAllArgConstructor() {
        // Act
        ErrorResponse response = new ErrorResponse(TEST_TIMESTAMP, TEST_STATUS, TEST_ERROR, TEST_MESSAGE, TEST_PATH);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getTimestamp()).isEqualTo(TEST_TIMESTAMP);
        assertThat(response.getStatus()).isEqualTo(TEST_STATUS);
        assertThat(response.getError()).isEqualTo(TEST_ERROR);
        assertThat(response.getMessage()).isEqualTo(TEST_MESSAGE);
        assertThat(response.getPath()).isEqualTo(TEST_PATH);
    }

    @Test
    @DisplayName("Should set and get timestamp")
    void shouldSetAndGetTimestamp() {
        // Arrange
        ErrorResponse response = new ErrorResponse();

        // Act
        response.setTimestamp(TEST_TIMESTAMP);

        // Assert
        assertThat(response.getTimestamp()).isEqualTo(TEST_TIMESTAMP);
    }

    @Test
    @DisplayName("Should set and get status")
    void shouldSetAndGetStatus() {
        // Arrange
        ErrorResponse response = new ErrorResponse();

        // Act
        response.setStatus(TEST_STATUS);

        // Assert
        assertThat(response.getStatus()).isEqualTo(TEST_STATUS);
    }

    @Test
    @DisplayName("Should set and get error")
    void shouldSetAndGetError() {
        // Arrange
        ErrorResponse response = new ErrorResponse();

        // Act
        response.setError(TEST_ERROR);

        // Assert
        assertThat(response.getError()).isEqualTo(TEST_ERROR);
    }

    @Test
    @DisplayName("Should set and get message")
    void shouldSetAndGetMessage() {
        // Arrange
        ErrorResponse response = new ErrorResponse();

        // Act
        response.setMessage(TEST_MESSAGE);

        // Assert
        assertThat(response.getMessage()).isEqualTo(TEST_MESSAGE);
    }

    @Test
    @DisplayName("Should set and get path")
    void shouldSetAndGetPath() {
        // Arrange
        ErrorResponse response = new ErrorResponse();

        // Act
        response.setPath(TEST_PATH);

        // Assert
        assertThat(response.getPath()).isEqualTo(TEST_PATH);
    }

    @Test
    @DisplayName("Should handle different status codes")
    void shouldHandleDifferentStatusCodes() {
        // Arrange
        ErrorResponse response = new ErrorResponse();
        int serverErrorStatus = 500;

        // Act
        response.setStatus(serverErrorStatus);

        // Assert
        assertThat(response.getStatus()).isEqualTo(serverErrorStatus);
    }

    @Test
    @DisplayName("Should handle null values in ErrorResponse")
    void shouldHandleNullValuesInErrorResponse() {
        // Arrange
        ErrorResponse response = new ErrorResponse();

        // Act & Assert - Should not throw exception
        response.setTimestamp(null);
        response.setError(null);
        response.setMessage(null);
        response.setPath(null);

        assertThat(response.getTimestamp()).isNull();
        assertThat(response.getError()).isNull();
        assertThat(response.getMessage()).isNull();
        assertThat(response.getPath()).isNull();
    }

    @Test
    @DisplayName("Should create multiple ErrorResponse instances independently")
    void shouldCreateMultipleErrorResponseInstancesIndependently() {
        // Arrange
        LocalDateTime timestamp1 = LocalDateTime.of(2026, 2, 6, 10, 0, 0);
        LocalDateTime timestamp2 = LocalDateTime.of(2026, 2, 6, 11, 0, 0);

        // Act
        ErrorResponse response1 = new ErrorResponse(timestamp1, 400, "Bad Request", "Error 1", "/path1");
        ErrorResponse response2 = new ErrorResponse(timestamp2, 500, "Server Error", "Error 2", "/path2");

        // Assert
        assertThat(response1.getTimestamp()).isEqualTo(timestamp1);
        assertThat(response2.getTimestamp()).isEqualTo(timestamp2);
        assertThat(response1.getStatus()).isEqualTo(400);
        assertThat(response2.getStatus()).isEqualTo(500);
    }
}
