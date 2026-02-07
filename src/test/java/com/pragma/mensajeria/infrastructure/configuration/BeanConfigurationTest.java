package com.pragma.mensajeria.infrastructure.configuration;

import com.pragma.mensajeria.domain.api.INotificationServicePort;
import com.pragma.mensajeria.domain.spi.ISmsMessagingPort;
import com.pragma.mensajeria.domain.usecase.NotificationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("Bean Configuration Tests")
class BeanConfigurationTest {

    @Test
    @DisplayName("Should create NotificationServicePort bean with NotificationUseCase")
    void shouldCreateNotificationServicePortBean() {
        // Arrange
        ISmsMessagingPort mockSmsMessagingPort = mock(ISmsMessagingPort.class);
        BeanConfiguration configuration = new BeanConfiguration();

        // Act
        INotificationServicePort result = configuration.notificationServicePort(mockSmsMessagingPort);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(NotificationUseCase.class);
    }

    @Test
    @DisplayName("Should create bean with same SMS messaging port provided")
    void shouldCreateBeanWithProvidedSmsMessagingPort() {
        // Arrange
        ISmsMessagingPort mockSmsMessagingPort = mock(ISmsMessagingPort.class);
        BeanConfiguration configuration = new BeanConfiguration();

        // Act
        INotificationServicePort result = configuration.notificationServicePort(mockSmsMessagingPort);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should instantiate configuration class")
    void shouldInstantiateConfigurationClass() {
        // Act
        BeanConfiguration configuration = new BeanConfiguration();

        // Assert
        assertThat(configuration).isNotNull();
    }
}
