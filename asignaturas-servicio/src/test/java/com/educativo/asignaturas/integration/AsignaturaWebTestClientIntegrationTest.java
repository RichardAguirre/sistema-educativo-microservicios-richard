package com.educativo.asignaturas.integration;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
 
import com.educativo.asignaturas.model.Asignatura;
import com.educativo.asignaturas.repository.AsignaturaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class AsignaturaWebTestClientIntegrationTest {

    @Autowired
    private ApplicationContext context;
    
    private WebTestClient webTestClient;
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    @BeforeEach
    public void setup() {
        this.webTestClient = WebTestClient
            .bindToApplicationContext(context)
            .apply(springSecurity())
            .configureClient()
            .build();
        
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
    @WithMockUser(username = "admin", roles = {"ADMIN"})  // Simulamos usuario ADMIN
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