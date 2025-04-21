package com.educativo.matriculas.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    
    @MockBean
    private DiscoveryClient discoveryClient;
    
    @Bean
    @Primary
    public HealthEndpoint healthEndpoint() {
        HealthEndpoint mockHealthEndpoint = mock(HealthEndpoint.class);
        when(mockHealthEndpoint.health()).thenReturn(Health.up().build());
        return mockHealthEndpoint;
    }
    
    @Bean
    @Primary
    public MetricsEndpoint metricsEndpoint() {
        return mock(MetricsEndpoint.class);
    }
}