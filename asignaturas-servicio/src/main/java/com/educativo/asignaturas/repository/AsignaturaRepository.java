package com.educativo.asignaturas.repository;

import com.educativo.asignaturas.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
}