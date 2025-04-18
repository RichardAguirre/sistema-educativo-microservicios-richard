package com.educativo.matriculas.model;

/**
 * Model class representing a user in the enrollment service
 */
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
    
    // Default constructor
    public Usuario() {
    }
    
    // Constructor with parameters
    public Usuario(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = "USER";
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}