package com.educativo.asignaturas.controller;

import com.educativo.asignaturas.model.Asignatura;
import com.educativo.asignaturas.repository.AsignaturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AsignaturaControllerTest {

    @InjectMocks
    private AsignaturaController asignaturaController;

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarAsignaturas() {
        // Arrange
        Asignatura asignatura1 = new Asignatura();
        asignatura1.setId(1L);
        asignatura1.setNombre("Programación I");
        asignatura1.setCodigo("PROG101");
        asignatura1.setCreditos(3);
        
        Asignatura asignatura2 = new Asignatura();
        asignatura2.setId(2L);
        asignatura2.setNombre("Bases de Datos");
        asignatura2.setCodigo("BD201");
        asignatura2.setCreditos(4);
        
        List<Asignatura> asignaturas = Arrays.asList(asignatura1, asignatura2);
        
        when(asignaturaRepository.findAll()).thenReturn(asignaturas);
        
        // Act
        List<Asignatura> result = asignaturaController.listarAsignaturas();
        
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Programación I", result.get(0).getNombre());
        assertEquals("Bases de Datos", result.get(1).getNombre());
        
        verify(asignaturaRepository, times(1)).findAll();
    }
}