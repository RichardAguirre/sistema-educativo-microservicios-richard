package com.educativo.matriculas.repository;

import com.educativo.matriculas.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByUsuarioId(Long usuarioId);
}