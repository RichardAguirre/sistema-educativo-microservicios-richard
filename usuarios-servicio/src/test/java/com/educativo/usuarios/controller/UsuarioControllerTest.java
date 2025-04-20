package com.educativo.usuarios.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import com.educativo.usuarios.model.Usuario;
import com.educativo.usuarios.repository.UsuarioRepository;

public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testObtenerUsuario() {
        Long usuarioId = 1L;
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(usuarioId);
        usuarioMock.setNombre("Usuario Test");
        usuarioMock.setEmail("test@example.com");
        usuarioMock.setRol("USER");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioMock));

        Usuario resultado = usuarioController.obtenerUsuario(usuarioId);

        assertNotNull(resultado);
        assertEquals(usuarioId, resultado.getId());
        assertEquals("Usuario Test", resultado.getNombre());
        assertEquals("test@example.com", resultado.getEmail());
        assertEquals("USER", resultado.getRol());
        
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testListarUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Usuario 1");
        usuario1.setEmail("usuario1@example.com");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Usuario 2");
        usuario2.setEmail("usuario2@example.com");

        List<Usuario> usuariosMock = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuariosMock);

        List<Usuario> resultado = usuarioController.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(usuario1.getId(), resultado.get(0).getId());
        assertEquals(usuario2.getNombre(), resultado.get(1).getNombre());

        verify(usuarioRepository, times(1)).findAll();
    }
}