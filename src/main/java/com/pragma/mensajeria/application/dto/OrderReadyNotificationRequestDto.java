package com.pragma.mensajeria.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReadyNotificationRequestDto {

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotBlank(message = "Security PIN is required")
    private String securityPin;

    @NotBlank(message = "Restaurant name is required")
    private String restaurantName;
}
