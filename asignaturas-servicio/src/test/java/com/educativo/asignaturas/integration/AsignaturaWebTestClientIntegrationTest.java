package com.educativo.asignaturas.integration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
 
import com.educativo.asignaturas.model.Asignatura;
import com.educativo.asignaturas.repository.AsignaturaRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AsignaturaWebTestClientIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    
    private WebTestClient webTestClient;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    @BeforeEach
    public void setup() {
        // Configurar MockMvc con soporte de seguridad explícito
        MockMvc mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
            
        this.webTestClient = MockMvcWebTestClient.bindTo(mockMvc).build();

        // Cargar datos de prueba
        if (asignaturaRepository.count() == 0) {
            Asignatura prog1 = new Asignatura();
            prog1.setNombre("Programación I");
            prog1.setCodigo("PROG101");
            prog1.setCreditos(3);
            asignaturaRepository.save(prog1);
        }
    }
    
    @Test
    public void testListarAsignaturas() {
        webTestClient.get()
            .uri("/asignaturas")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Asignatura.class)
            .value(list -> assertThat(list).isNotEmpty());
    }
    
    @Test
    public void testObtenerAsignaturaPorId() {
        Asignatura primeraAsignatura = asignaturaRepository.findAll().get(0);
        
        webTestClient.get()
            .uri("/asignaturas/{id}", primeraAsignatura.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.id").isEqualTo(primeraAsignatura.getId())
            .jsonPath("$.nombre").isEqualTo(primeraAsignatura.getNombre());
    }
    
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCrearAsignatura() {
        Asignatura nuevaAsignatura = new Asignatura();
        nuevaAsignatura.setNombre("Asignatura Test Integration");
        nuevaAsignatura.setCodigo("TEST101");
        nuevaAsignatura.setCreditos(4);
        
        webTestClient.post()
            .uri("/asignaturas/crear")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(nuevaAsignatura)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.nombre").isEqualTo("Asignatura Test Integration")
            .jsonPath("$.codigo").isEqualTo("TEST101")
            .jsonPath("$.creditos").isEqualTo(4);
    }
}