package com.educativo.matriculas.client;

import com.educativo.matriculas.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-servicio")
public interface UsuarioClient {
    @GetMapping("/usuarios/{id}")
    Usuario getUsuarioById(@PathVariable("id") Long id);
}