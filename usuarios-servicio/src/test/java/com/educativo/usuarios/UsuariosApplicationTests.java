package com.educativo.usuarios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UsuariosApplicationTests {

    @MockBean
    private HealthEndpoint healthEndpoint;
    
    @MockBean
    private MetricsEndpoint metricsEndpoint;

    @Test
    void contextLoads() {
    }
}