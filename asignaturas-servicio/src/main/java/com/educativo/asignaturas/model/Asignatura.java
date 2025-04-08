package com.educativo.asignaturas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String codigo;
    private Integer creditos;
    
    // Default constructor
    public Asignatura() {
    }
    
    // Constructor with parameters
    public Asignatura(Long id, String nombre, String codigo, Integer creditos) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.creditos = creditos;
    }
    
    // Getters and setters unchanged
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public Integer getCreditos() {
        return creditos;
    }
    
    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
}