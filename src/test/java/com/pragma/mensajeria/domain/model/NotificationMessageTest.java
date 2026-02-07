package com.pragma.mensajeria.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Notification Message Tests")
class NotificationMessageTest {

    private static final String VALID_PHONE = "+573001234567";
    private static final String VALID_MESSAGE_CONTENT = "Your order is ready";
    private static final String VALID_ORDER_ID = "123";
    private static final String VALID_SECURITY_PIN = "456789";
    private static final String VALID_RESTAURANT_NAME = "Mi Restaurante";

    @Test
    @DisplayName("Should create NotificationMessage with no-arg constructor")
    void shouldCreateNotificationMessageWithNoArgConstructor() {
        // Act
        NotificationMessage message = new NotificationMessage();

        // Assert
        assertThat(message).isNotNull();
    }

    @Test
    @DisplayName("Should create NotificationMessage with all-arg constructor")
    void shouldCreateNotificationMessageWithAllArgConstructor() {
        // Act
        NotificationMessage message = new NotificationMessage(
                VALID_PHONE,
                VALID_MESSAGE_CONTENT,
                VALID_ORDER_ID,
                VALID_SECURITY_PIN,
                VALID_RESTAURANT_NAME
        );

        // Assert
        assertThat(message).isNotNull();
        assertThat(message.getPhoneNumber()).isEqualTo(VALID_PHONE);
        assertThat(message.getMessageContent()).isEqualTo(VALID_MESSAGE_CONTENT);
        assertThat(message.getOrderId()).isEqualTo(VALID_ORDER_ID);
        assertThat(message.getSecurityPin()).isEqualTo(VALID_SECURITY_PIN);
        assertThat(message.getRestaurantName()).isEqualTo(VALID_RESTAURANT_NAME);
    }

    @Test
    @DisplayName("Should set and get phone number")
    void shouldSetAndGetPhoneNumber() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setPhoneNumber(VALID_PHONE);

        // Assert
        assertThat(message.getPhoneNumber()).isEqualTo(VALID_PHONE);
    }

    @Test
    @DisplayName("Should set and get message content")
    void shouldSetAndGetMessageContent() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setMessageContent(VALID_MESSAGE_CONTENT);

        // Assert
        assertThat(message.getMessageContent()).isEqualTo(VALID_MESSAGE_CONTENT);
    }

    @Test
    @DisplayName("Should set and get order ID")
    void shouldSetAndGetOrderId() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setOrderId(VALID_ORDER_ID);

        // Assert
        assertThat(message.getOrderId()).isEqualTo(VALID_ORDER_ID);
    }

    @Test
    @DisplayName("Should set and get security PIN")
    void shouldSetAndGetSecurityPin() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setSecurityPin(VALID_SECURITY_PIN);

        // Assert
        assertThat(message.getSecurityPin()).isEqualTo(VALID_SECURITY_PIN);
    }

    @Test
    @DisplayName("Should set and get restaurant name")
    void shouldSetAndGetRestaurantName() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setRestaurantName(VALID_RESTAURANT_NAME);

        // Assert
        assertThat(message.getRestaurantName()).isEqualTo(VALID_RESTAURANT_NAME);
    }

    @Test
    @DisplayName("Should handle multiple field updates independently")
    void shouldHandleMultipleFieldUpdatesIndependently() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setPhoneNumber(VALID_PHONE);
        message.setOrderId(VALID_ORDER_ID);
        message.setSecurityPin(VALID_SECURITY_PIN);
        message.setRestaurantName(VALID_RESTAURANT_NAME);
        message.setMessageContent(VALID_MESSAGE_CONTENT);

        // Assert
        assertThat(message.getPhoneNumber()).isEqualTo(VALID_PHONE);
        assertThat(message.getOrderId()).isEqualTo(VALID_ORDER_ID);
        assertThat(message.getSecurityPin()).isEqualTo(VALID_SECURITY_PIN);
        assertThat(message.getRestaurantName()).isEqualTo(VALID_RESTAURANT_NAME);
        assertThat(message.getMessageContent()).isEqualTo(VALID_MESSAGE_CONTENT);
    }

    @Test
    @DisplayName("Should handle null phone number")
    void shouldHandleNullPhoneNumber() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setPhoneNumber(null);

        // Assert
        assertThat(message.getPhoneNumber()).isNull();
    }

    @Test
    @DisplayName("Should handle null message content")
    void shouldHandleNullMessageContent() {
        // Arrange
        NotificationMessage message = new NotificationMessage();

        // Act
        message.setMessageContent(null);

        // Assert
        assertThat(message.getMessageContent()).isNull();
    }

    @Test
    @DisplayName("Should handle different phone numbers")
    void shouldHandleDifferentPhoneNumbers() {
        // Arrange
        NotificationMessage message = new NotificationMessage();
        String phone1 = "+573001234567";
        String phone2 = "+573009876543";

        // Act & Assert
        message.setPhoneNumber(phone1);
        assertThat(message.getPhoneNumber()).isEqualTo(phone1);

        message.setPhoneNumber(phone2);
        assertThat(message.getPhoneNumber()).isEqualTo(phone2);
    }

    @Test
    @DisplayName("Should create multiple instances independently")
    void shouldCreateMultipleInstancesIndependently() {
        // Act
        NotificationMessage message1 = new NotificationMessage(
                "+573001234567",
                "Content1",
                "Order1",
                "Pin1",
                "Restaurant1"
        );

        NotificationMessage message2 = new NotificationMessage(
                "+573009876543",
                "Content2",
                "Order2",
                "Pin2",
                "Restaurant2"
        );

        // Assert
        assertThat(message1.getPhoneNumber()).isEqualTo("+573001234567");
        assertThat(message2.getPhoneNumber()).isEqualTo("+573009876543");
        assertThat(message1.getOrderId()).isEqualTo("Order1");
        assertThat(message2.getOrderId()).isEqualTo("Order2");
    }
}
