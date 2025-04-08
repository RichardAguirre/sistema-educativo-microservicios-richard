package com.educativo.usuarios.repository;

import com.educativo.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}