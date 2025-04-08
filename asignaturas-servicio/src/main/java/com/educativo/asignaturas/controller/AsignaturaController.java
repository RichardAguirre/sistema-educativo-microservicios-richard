package com.educativo.asignaturas.controller;

import com.educativo.asignaturas.model.Asignatura;
import com.educativo.asignaturas.repository.AsignaturaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
public class AsignaturaController {
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    
    // Constructor that initializes some sample courses
    @PostConstruct
    public void init() {
        // Only add if repository is empty
        if (asignaturaRepository.count() == 0) {
            Asignatura prog1 = new Asignatura();
            prog1.setNombre("Programación I");
            prog1.setCodigo("PROG101");
            prog1.setCreditos(3);
            asignaturaRepository.save(prog1);
            
            Asignatura bd = new Asignatura();
            bd.setNombre("Bases de Datos");
            bd.setCodigo("BD201");
            bd.setCreditos(4);
            asignaturaRepository.save(bd);
        }
    }
    
    @GetMapping
    public List<Asignatura> listarAsignaturas() {
        return asignaturaRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Asignatura obtenerAsignatura(@PathVariable Long id) {
        return asignaturaRepository.findById(id).orElse(null);
    }
    
    @PostMapping
    public Asignatura crearAsignatura(@RequestBody Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }
}