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
public class NotificationMessage {

    private String phoneNumber;
    private String messageContent;
    private String orderId;
    private String securityPin;
    private String restaurantName;
}
