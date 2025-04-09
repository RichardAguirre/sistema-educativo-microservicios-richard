package com.educativo.asignaturas;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.application.name=asignaturas-servicio-test",
    "server.port=0"
})
class AsignaturasApplicationTests {

    @MockBean
    private HealthEndpoint healthEndpoint;
    
    @MockBean
    private MetricsEndpoint metricsEndpoint;

    @Test
    void contextLoads() {
    }
}