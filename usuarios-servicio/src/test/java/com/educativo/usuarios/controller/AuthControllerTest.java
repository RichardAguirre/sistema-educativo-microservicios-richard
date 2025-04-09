package com.educativo.usuarios.controller;

import com.educativo.usuarios.dto.JwtResponse;
import com.educativo.usuarios.dto.LoginRequest;
import com.educativo.usuarios.model.Usuario;
import com.educativo.usuarios.repository.UsuarioRepository;
import com.educativo.usuarios.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        // Arrange
        String email = "admin@example.com";
        String password = "admin123";
        String token = "test-jwt-token";
        
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Admin Usuario");
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol("ADMIN");
        
        User userDetails = new User(
            email,
            password,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        
        // Mock authentication
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        
        // Mock JWT token generation
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(token);
        
        // Mock repository response
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
        
        // Act
        ResponseEntity<?> response = authController.login(loginRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse);
        assertEquals(token, jwtResponse.getToken());
        assertEquals(email, jwtResponse.getEmail());
        assertEquals("ADMIN", jwtResponse.getRol());
        assertEquals("Admin Usuario", jwtResponse.getNombre());
        assertEquals(1L, jwtResponse.getId());
    }
}