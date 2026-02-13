package com.pragma.mensajeria.infrastructure.input.rest.controller;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;
import com.pragma.mensajeria.application.handler.INotificationHandler;
import com.pragma.mensajeria.infrastructure.constant.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.NOTIFICATIONS_BASE_PATH)
@Tag(name = "Notifications", description = "Notification management API for sending messages to clients")
@SecurityRequirement(name = "bearerAuth")
public class NotificationRestController {

    private final INotificationHandler notificationHandler;

    public NotificationRestController(INotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @Operation(summary = "Send order ready notification",
            description = "Sends an SMS notification to the client informing that their order is ready to be picked up. " +
                    "Includes a security PIN that the client must present to claim the order.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Notification sent successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Invalid input data - Missing required fields",
                    content = @Content),
            @ApiResponse(responseCode = "401",
                    description = "Not authenticated",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Failed to send notification",
                    content = @Content)
    })
    @PostMapping("/order-ready")
    public ResponseEntity<NotificationResponseDto> sendOrderReadyNotification(
            @Valid @RequestBody OrderReadyNotificationRequestDto request) {
        NotificationResponseDto response = notificationHandler.sendOrderReadyNotification(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
