package com.pragma.mensajeria.domain.model;

public class NotificationResult {

    private boolean success;
    private String messageId;
    private String errorMessage;

    public NotificationResult() {
    }

    public NotificationResult(boolean success, String messageId, String errorMessage) {
        this.success = success;
        this.messageId = messageId;
        this.errorMessage = errorMessage;
    }

    public static NotificationResult success(String messageId) {
        return new NotificationResult(true, messageId, null);
    }

    public static NotificationResult failure(String errorMessage) {
        return new NotificationResult(false, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
