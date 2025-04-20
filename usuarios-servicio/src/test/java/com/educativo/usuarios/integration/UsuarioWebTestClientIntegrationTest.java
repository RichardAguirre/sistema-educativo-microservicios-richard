package com.educativo.usuarios.integration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.educativo.usuarios.dto.LoginRequest;
import com.educativo.usuarios.dto.RegistroRequest;
import com.educativo.usuarios.model.Usuario;
import com.educativo.usuarios.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class UsuarioWebTestClientIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @BeforeEach
    public void setup() {
        Optional<Usuario> testUser = usuarioRepository.findByEmail("test-integration@example.com");
        testUser.ifPresent(usuario -> usuarioRepository.deleteById(usuario.getId()));
    }
    
    @Test
    public void testRegistroYLogin() {
        RegistroRequest registroRequest = new RegistroRequest(
            "Usuario Prueba",
            "test-integration@example.com",
            "password123"
        );
        
        webTestClient.post()
            .uri("/auth/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(registroRequest)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Usuario registrado exitosamente!");
        
        Optional<Usuario> usuarioCreado = usuarioRepository.findByEmail("test-integration@example.com");
        assertTrue(usuarioCreado.isPresent());
        
        LoginRequest loginRequest = new LoginRequest(
            "test-integration@example.com",
            "password123"
        );
        
        webTestClient.post()
            .uri("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(loginRequest)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.token").exists()
            .jsonPath("$.email").isEqualTo("test-integration@example.com")
            .jsonPath("$.nombre").isEqualTo("Usuario Prueba")
            .jsonPath("$.rol").isEqualTo("USER");
    }
}
