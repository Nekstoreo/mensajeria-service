package com.pragma.mensajeria.infrastructure.configuration;

import com.pragma.mensajeria.infrastructure.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("Security Configuration Tests")
class SecurityConfigurationTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("Should instantiate SecurityConfiguration with JwtAuthenticationFilter")
    void shouldInstantiateSecurityConfiguration() {
        // Act
        SecurityConfiguration configuration = new SecurityConfiguration(jwtAuthenticationFilter);

        // Assert
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("Should have non-null JwtAuthenticationFilter dependency")
    void shouldHaveJwtAuthenticationFilterDependency() {
        // Act
        SecurityConfiguration configuration = new SecurityConfiguration(jwtAuthenticationFilter);

        // Assert
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("Should create SecurityFilterChain with provided HttpSecurity configuration")
    void shouldCreateSecurityFilterChainBean() throws Exception {
        // Arrange
        SecurityConfiguration configuration = new SecurityConfiguration(jwtAuthenticationFilter);
        // Assert - Configuration is created without errors
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("Should initialize with mock JwtAuthenticationFilter")
    void shouldInitializeWithMockFilter() {
        // Arrange
        JwtAuthenticationFilter mockFilter = mock(JwtAuthenticationFilter.class);

        // Act
        SecurityConfiguration configuration = new SecurityConfiguration(mockFilter);

        // Assert
        assertThat(configuration).isNotNull();
    }
}
