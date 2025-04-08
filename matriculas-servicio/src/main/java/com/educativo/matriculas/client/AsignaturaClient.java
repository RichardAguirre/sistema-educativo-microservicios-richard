package com.educativo.matriculas.client;

import com.educativo.matriculas.model.Asignatura;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "asignaturas-servicio")
public interface AsignaturaClient {
    @GetMapping("/asignaturas/{id}")
    Asignatura getAsignaturaById(@PathVariable("id") Long id);
}