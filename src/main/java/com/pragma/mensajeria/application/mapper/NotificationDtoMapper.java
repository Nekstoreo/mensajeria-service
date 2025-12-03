package com.pragma.mensajeria.application.mapper;

import com.pragma.mensajeria.application.dto.NotificationResponseDto;
import com.pragma.mensajeria.application.dto.OrderReadyNotificationRequestDto;
import com.pragma.mensajeria.domain.model.NotificationMessage;
import com.pragma.mensajeria.domain.model.NotificationResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationDtoMapper {

    NotificationMessage toNotificationMessage(OrderReadyNotificationRequestDto dto);

    @Mapping(target = "message", expression = "java(result.isSuccess() ? \"Notification sent successfully\" : result.getErrorMessage())")
    NotificationResponseDto toNotificationResponseDto(NotificationResult result);
}
