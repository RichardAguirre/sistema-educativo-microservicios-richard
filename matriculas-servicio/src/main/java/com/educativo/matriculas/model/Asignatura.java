package com.educativo.matriculas.model;

/**
 * Model class representing a course/subject
 */
public class Asignatura {
    private Long id;
    private String nombre;
    private Integer creditos;
    private String codigo;
    
    // Default constructor
    public Asignatura() {
    }
    
    // Constructor with parameters
    public Asignatura(Long id, String nombre, Integer creditos, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
        this.codigo = codigo;
    }
    
    // Getters and setters
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
    
    public Integer getCreditos() {
        return creditos;
    }
    
    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}