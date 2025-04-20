package com.educativo.matriculas.integration;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.educativo.matriculas.client.AsignaturaClient;
import com.educativo.matriculas.client.UsuarioClient;
import com.educativo.matriculas.model.Asignatura;
import com.educativo.matriculas.model.Usuario;

@SpringBootTest
@AutoConfigureMockMvc
public class MatriculaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UsuarioClient usuarioClient;
    
    @MockBean
    private AsignaturaClient asignaturaClient;
    
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testFlujoMatriculaCompleto() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setEmail("test@example.com");
        
        Asignatura asignatura = new Asignatura();
        asignatura.setId(1L);
        asignatura.setNombre("Matemáticas");
        asignatura.setCodigo("MAT101");
        
        when(usuarioClient.getUsuarioById(anyLong())).thenReturn(usuario);
        when(asignaturaClient.getAsignaturaById(anyLong())).thenReturn(asignatura);
        
        mockMvc.perform(post("/matriculas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuarioId\": 1, \"asignaturasIds\": [1], \"periodo\": \"2025-1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.matricula.usuarioId").value(1))
                .andExpect(jsonPath("$.usuario.nombre").value("Test User"));
        
        mockMvc.perform(get("/matriculas/usuario/1"))
                .andExpect(status().isOk());
    }
}