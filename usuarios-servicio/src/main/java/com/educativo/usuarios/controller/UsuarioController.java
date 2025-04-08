package com.educativo.usuarios.controller;

import com.educativo.usuarios.model.Usuario;
import com.educativo.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}