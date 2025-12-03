package com.pragma.mensajeria.infrastructure.configuration;

import com.pragma.mensajeria.domain.api.INotificationServicePort;
import com.pragma.mensajeria.domain.spi.ISmsMessagingPort;
import com.pragma.mensajeria.domain.usecase.NotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public INotificationServicePort notificationServicePort(ISmsMessagingPort smsMessagingPort) {
        return new NotificationUseCase(smsMessagingPort);
    }
}
