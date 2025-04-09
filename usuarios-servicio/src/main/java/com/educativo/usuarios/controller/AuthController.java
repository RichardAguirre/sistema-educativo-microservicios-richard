package com.educativo.usuarios.controller;

import com.educativo.usuarios.dto.JwtResponse;
import com.educativo.usuarios.dto.LoginRequest;
import com.educativo.usuarios.dto.RegistroRequest;
import com.educativo.usuarios.model.Usuario;
import com.educativo.usuarios.repository.UsuarioRepository;
import com.educativo.usuarios.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        
        return ResponseEntity.ok(new JwtResponse(jwt, 
                                                usuario.getId(), 
                                                usuario.getNombre(), 
                                                usuario.getEmail(), 
                                                usuario.getRol()));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody RegistroRequest registroRequest) {
        if (usuarioRepository.findByEmail(registroRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email ya está en uso!");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(registroRequest.getNombre());
        usuario.setEmail(registroRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));
        usuario.setRol("USER"); // Por defecto asignamos rol USER

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente!");
    }
}