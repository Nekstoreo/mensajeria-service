package com.pragma.mensajeria.domain.model;

public class NotificationMessage {

    private String phoneNumber;
    private String message;
    private String orderId;
    private String securityPin;
    private String restaurantName;

    public NotificationMessage() {
    }

    public NotificationMessage(String phoneNumber, String message, String orderId, String securityPin, String restaurantName) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.orderId = orderId;
        this.securityPin = securityPin;
        this.restaurantName = restaurantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSecurityPin() {
        return securityPin;
    }

    public void setSecurityPin(String securityPin) {
        this.securityPin = securityPin;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
