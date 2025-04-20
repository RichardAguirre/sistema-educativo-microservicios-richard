package com.educativo.matriculas.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.educativo.matriculas.client.AsignaturaClient;
import com.educativo.matriculas.client.UsuarioClient;
import com.educativo.matriculas.model.Asignatura;
import com.educativo.matriculas.model.Matricula;
import com.educativo.matriculas.model.Usuario;
import com.educativo.matriculas.repository.MatriculaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MatriculaWebTestClientIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private UsuarioClient usuarioClient;
    
    @MockBean
    private AsignaturaClient asignaturaClient;
    
    @Autowired
    private MatriculaRepository matriculaRepository;
    
    @BeforeEach
    public void setup() {
        matriculaRepository.deleteAll();
        
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setEmail("test@example.com");
        usuario.setRol("ADMIN");
        
        Asignatura asignatura = new Asignatura();
        asignatura.setId(1L);
        asignatura.setNombre("Matemáticas");
        asignatura.setCodigo("MAT101");
        
        when(usuarioClient.getUsuarioById(anyLong())).thenReturn(usuario);
        when(asignaturaClient.getAsignaturaById(anyLong())).thenReturn(asignatura);
    }
    
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testFlujoMatriculaCompleto() {
        webTestClient.post()
            .uri("/matriculas")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{\"usuarioId\": 1, \"asignaturasIds\": [1], \"periodo\": \"2025-1\"}")
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.matricula.usuarioId").isEqualTo(1)
            .jsonPath("$.usuario.nombre").isEqualTo("Test User");

        webTestClient.get()
            .uri("/matriculas/usuario/1")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Matricula.class);
    }
}