package com.educativo.asignaturas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.educativo.asignaturas.config.TestConfig;

@SpringBootTest(classes = {AsignaturasApplication.class, TestConfig.class})
@ActiveProfiles("test")
class AsignaturasApplicationTests {

    @MockBean
    private HealthEndpoint healthEndpoint;
    
    @MockBean
    private MetricsEndpoint metricsEndpoint;

    @Test
    void contextLoads() {
    }
}