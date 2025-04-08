package com.educativo.matriculas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long usuarioId;
    
    @ElementCollection
    private List<Long> asignaturasIds = new ArrayList<>();
    
    private LocalDate fechaMatricula;
    private String periodo;
    
    public Matricula() {
        this.fechaMatricula = LocalDate.now();
    }
    
    public Matricula(Long id, Long usuarioId, List<Long> asignaturasIds, String periodo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.asignaturasIds = asignaturasIds;
        this.fechaMatricula = LocalDate.now();
        this.periodo = periodo;
    }
    
    // Getters and setters unchanged
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public List<Long> getAsignaturasIds() {
        return asignaturasIds;
    }
    
    public void setAsignaturasIds(List<Long> asignaturasIds) {
        this.asignaturasIds = asignaturasIds;
    }
    
    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }
    
    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}