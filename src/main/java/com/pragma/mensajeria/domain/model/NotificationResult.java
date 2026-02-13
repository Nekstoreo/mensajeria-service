package com.pragma.mensajeria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResult {

    private boolean success;
    private String messageId;
    private String errorMessage;

    public static NotificationResult success(String messageId) {
        return new NotificationResult(true, messageId, null);
    }

    public static NotificationResult failure(String errorMessage) {
        return new NotificationResult(false, null, errorMessage);
    }
}
