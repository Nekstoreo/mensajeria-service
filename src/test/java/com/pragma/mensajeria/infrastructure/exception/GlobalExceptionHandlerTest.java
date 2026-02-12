package com.pragma.mensajeria.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Global Exception Handler Tests")
class GlobalExceptionHandlerTest {

    private static final String BAD_REQUEST = "Bad Request";
    private static final String VALIDATION_ERROR = "Validation Error";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String TEST_REQUEST_URI = "/api/v1/notifications";
    private static final String ILLEGAL_ARGUMENT_MESSAGE = "Invalid argument provided";
    private static final String GENERIC_ERROR_PREFIX = "An unexpected error occurred: ";

    private GlobalExceptionHandler globalExceptionHandler;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURI()).thenReturn(TEST_REQUEST_URI);
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException with BAD_REQUEST status")
    void shouldHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(ex, mockRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().getError()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo(ILLEGAL_ARGUMENT_MESSAGE);
        assertThat(response.getBody().getPath()).isEqualTo(TEST_REQUEST_URI);
    }

    @Test
    @DisplayName("Should include timestamp in IllegalArgumentException response")
    void shouldIncludeTimestampInIllegalArgumentExceptionResponse() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        LocalDateTime beforeCall = LocalDateTime.now();

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(ex, mockRequest);

        LocalDateTime afterCall = LocalDateTime.now();

        // Assert
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getTimestamp()).isAfterOrEqualTo(beforeCall);
        assertThat(response.getBody().getTimestamp()).isBeforeOrEqualTo(afterCall);
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with single field error")
    void shouldHandleMethodArgumentNotValidExceptionWithSingleFieldError() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<>();
        String fieldName = "phoneNumber";
        String errorMessage = "Phone number is required";
        fieldErrors.add(new FieldError("request", fieldName, errorMessage));

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex, mockRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().getError()).isEqualTo(VALIDATION_ERROR);
        assertThat(response.getBody().getMessage()).isEqualTo(errorMessage);
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with multiple field errors")
    void shouldHandleMethodArgumentNotValidExceptionWithMultipleFieldErrors() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("request", "phoneNumber", "Phone number is required"));
        fieldErrors.add(new FieldError("request", "orderId", "Order ID is required"));

        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex, mockRequest);

        // Assert
        // The handler should return the first field error message as main message
        assertThat(response.getBody().getMessage()).isEqualTo("Phone number is required");
    }

    @Test
    @DisplayName("Should handle generic Exception with INTERNAL_SERVER_ERROR status")
    void shouldHandleGenericException() {
        // Arrange
        String originalMessage = "Database connection failed";
        Exception ex = new Exception(originalMessage);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(ex, mockRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getBody().getError()).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getMessage()).contains(GENERIC_ERROR_PREFIX);
        assertThat(response.getBody().getMessage()).contains(originalMessage);
    }

    @Test
    @DisplayName("Should include timestamp in generic Exception response")
    void shouldIncludeTimestampInGenericExceptionResponse() {
        // Arrange
        Exception ex = new Exception("Test error");
        LocalDateTime beforeCall = LocalDateTime.now();

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(ex, mockRequest);

        LocalDateTime afterCall = LocalDateTime.now();

        // Assert
        assertThat(response.getBody().getTimestamp()).isNotNull();
        assertThat(response.getBody().getTimestamp()).isAfterOrEqualTo(beforeCall);
        assertThat(response.getBody().getTimestamp()).isBeforeOrEqualTo(afterCall);
    }

    @Test
    @DisplayName("Should set correct request URI in ErrorResponse")
    void shouldSetCorrectRequestUriInErrorResponse() {
        // Arrange
        String customUri = "/api/v1/custom/path";
        when(mockRequest.getRequestURI()).thenReturn(customUri);
        Exception ex = new Exception("Test error");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(ex, mockRequest);

        // Assert
        assertThat(response.getBody().getPath()).isEqualTo(customUri);
    }

    @Test
    @DisplayName("Should handle exception with null message")
    void shouldHandleExceptionWithNullMessage() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException();

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(ex, mockRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with empty field errors")
    void shouldHandleMethodArgumentNotValidExceptionWithEmptyFieldErrors() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>());

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex, mockRequest);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
