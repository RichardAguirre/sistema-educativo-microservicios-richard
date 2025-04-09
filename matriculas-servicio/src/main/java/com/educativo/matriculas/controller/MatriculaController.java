package com.educativo.matriculas.controller;

import com.educativo.matriculas.client.AsignaturaClient;
import com.educativo.matriculas.client.UsuarioClient;
import com.educativo.matriculas.model.Asignatura;
import com.educativo.matriculas.model.Matricula;
import com.educativo.matriculas.model.Usuario;
import com.educativo.matriculas.repository.MatriculaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    
    private final UsuarioClient usuarioClient;
    private final AsignaturaClient asignaturaClient;
    private final MatriculaRepository matriculaRepository;
    
    @Autowired
    public MatriculaController(UsuarioClient usuarioClient, AsignaturaClient asignaturaClient, 
                               MatriculaRepository matriculaRepository) {
        this.usuarioClient = usuarioClient;
        this.asignaturaClient = asignaturaClient;
        this.matriculaRepository = matriculaRepository;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> crearMatricula(@RequestBody Matricula matricula) {
        try {
            // Validate that the user exists
            Usuario usuario = usuarioClient.getUsuarioById(matricula.getUsuarioId());
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado con ID: " + matricula.getUsuarioId());
            }
            
            // Verify user is creating their own enrollment or is admin
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = auth.getName();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            // If not admin and trying to create enrollment for another user
            if (!isAdmin && !currentUsername.equals(usuario.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para crear matrículas para otro usuario");
            }
            
            // Validate that all subjects exist
            List<Asignatura> asignaturas = new ArrayList<>();
            for (Long asignaturaId : matricula.getAsignaturasIds()) {
                Asignatura asignatura = asignaturaClient.getAsignaturaById(asignaturaId);
                if (asignatura == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Asignatura no encontrada con ID: " + asignaturaId);
                }
                asignaturas.add(asignatura);
            }
            
            // Create matricula in database
            Matricula savedMatricula = matriculaRepository.save(matricula);
            
            // Prepare response with details
            Map<String, Object> response = new HashMap<>();
            response.put("matricula", savedMatricula);
            response.put("usuario", usuario);
            response.put("asignaturas", asignaturas);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la matrícula: " + e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> obtenerMatricula(@PathVariable Long id) {
        // Find matricula by ID
        Matricula matricula = matriculaRepository.findById(id).orElse(null);
                
        if (matricula == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Matrícula no encontrada con ID: " + id);
        }
        
        try {
            // Get detailed information about the user and subjects
            Usuario usuario = usuarioClient.getUsuarioById(matricula.getUsuarioId());
            
            // Verify user is viewing their own enrollment or is admin
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = auth.getName();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            // If not admin and trying to view enrollment of another user
            if (!isAdmin && !currentUsername.equals(usuario.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para ver matrículas de otro usuario");
            }
            
            List<Asignatura> asignaturas = new ArrayList<>();
            for (Long asignaturaId : matricula.getAsignaturasIds()) {
                Asignatura asignatura = asignaturaClient.getAsignaturaById(asignaturaId);
                asignaturas.add(asignatura);
            }
            
            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("matricula", matricula);
            response.put("usuario", usuario);
            response.put("asignaturas", asignaturas);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener detalles de la matrícula: " + e.getMessage());
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> obtenerMatriculasPorUsuario(@PathVariable Long usuarioId) {
        try {
            // Verify that the user exists
            Usuario usuario = usuarioClient.getUsuarioById(usuarioId);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado con ID: " + usuarioId);
            }
            
            // Verify user is viewing their own enrollments or is admin
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = auth.getName();
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            // If not admin and trying to view enrollments of another user
            if (!isAdmin && !currentUsername.equals(usuario.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para ver matrículas de otro usuario");
            }
            
            // Find matriculas by usuario
            List<Matricula> matriculasUsuario = matriculaRepository.findByUsuarioId(usuarioId);
            
            return ResponseEntity.ok(matriculasUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener matrículas del usuario: " + e.getMessage());
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }
}