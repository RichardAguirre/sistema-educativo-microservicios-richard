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
    public ResponseEntity<?> crearMatricula(@RequestBody Matricula matricula) {
        try {
            // Validate that the user exists
            Usuario usuario = usuarioClient.getUsuarioById(matricula.getUsuarioId());
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado con ID: " + matricula.getUsuarioId());
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
    public ResponseEntity<?> obtenerMatriculasPorUsuario(@PathVariable Long usuarioId) {
        try {
            // Verify that the user exists
            Usuario usuario = usuarioClient.getUsuarioById(usuarioId);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado con ID: " + usuarioId);
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
    public List<Matricula> listarMatriculas() {
        return matriculaRepository.findAll();
    }
}