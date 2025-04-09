package com.educativo.matriculas.controller;

import com.educativo.matriculas.client.AsignaturaClient;
import com.educativo.matriculas.client.UsuarioClient;
import com.educativo.matriculas.model.Asignatura;
import com.educativo.matriculas.model.Matricula;
import com.educativo.matriculas.model.Usuario;
import com.educativo.matriculas.repository.MatriculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import java.util.Collections;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MatriculaControllerTest {

    @InjectMocks
    private MatriculaController matriculaController;

    @Mock
    private MatriculaRepository matriculaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private AsignaturaClient asignaturaClient;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup Security Context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = { "ADMIN" })
    public void testCrearMatricula() {
        // Arrange
        Long usuarioId = 1L;
        Long asignatura1Id = 1L;
        Long asignatura2Id = 2L;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setNombre("Estudiante Uno");
        usuario.setEmail("admin@example.com");
        usuario.setRol("ADMIN");

        Asignatura asignatura1 = new Asignatura();
        asignatura1.setId(asignatura1Id);
        asignatura1.setNombre("Programación I");

        Asignatura asignatura2 = new Asignatura();
        asignatura2.setId(asignatura2Id);
        asignatura2.setNombre("Bases de Datos");

        Matricula matricula = new Matricula();
        matricula.setId(1L);
        matricula.setUsuarioId(usuarioId);
        matricula.setAsignaturasIds(Arrays.asList(asignatura1Id, asignatura2Id));

        // Mock authentication
        when(authentication.getName()).thenReturn("admin@example.com");

        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
        .when(authentication).getAuthorities();

        // Mock clients
        when(usuarioClient.getUsuarioById(usuarioId)).thenReturn(usuario);
        when(asignaturaClient.getAsignaturaById(asignatura1Id)).thenReturn(asignatura1);
        when(asignaturaClient.getAsignaturaById(asignatura2Id)).thenReturn(asignatura2);

        // Mock repository
        when(matriculaRepository.save(any(Matricula.class))).thenReturn(matricula);

        // Act
        ResponseEntity<?> response = matriculaController.crearMatricula(matricula);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(matricula, responseBody.get("matricula"));
        assertEquals(usuario, responseBody.get("usuario"));

        verify(usuarioClient, times(1)).getUsuarioById(usuarioId);
        verify(asignaturaClient, times(1)).getAsignaturaById(asignatura1Id);
        verify(asignaturaClient, times(1)).getAsignaturaById(asignatura2Id);
        verify(matriculaRepository, times(1)).save(any(Matricula.class));
    }
}