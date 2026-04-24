package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.service.EtudiantService;
import com.openclassrooms.etudiant.service.JwtService;
import com.openclassrooms.etudiant.entities.Etudiant;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantController.class)
@AutoConfigureMockMvc(addFilters = false)
class EtudiantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EtudiantService service;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnList() throws Exception {
        Mockito.when(service.findAll()).thenReturn(
                Arrays.asList(
                        new Etudiant(1L, "John", "Doe", "login"),
                        new Etudiant(2L, "Tony", "Debo", "TonyD")
                )
        );

        mockMvc.perform(get("/api/etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getOne_shouldReturnEtudiant() throws Exception {
        Mockito.when(service.findById(1L))
                .thenReturn(new Etudiant(1L, "John", "Doe", "login"));

        mockMvc.perform(get("/api/etudiants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getOne_shouldReturn400_whenNotFound() throws Exception {
        Mockito.when(service.findById(99L))
                .thenThrow(new RuntimeException("Etudiant not found"));

        mockMvc.perform(get("/api/etudiants/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_shouldReturnEtudiant() throws Exception {
        Etudiant input = new Etudiant(null, "John", "Doe", "login");
        Etudiant saved = new Etudiant(1L, "John", "Doe", "login");

        Mockito.when(service.create(Mockito.any())).thenReturn(saved);

        mockMvc.perform(post("/api/etudiants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void update_shouldReturnUpdatedEtudiant() throws Exception {
        Etudiant updated = new Etudiant(1L, "New", "Name", "newLogin");

        Mockito.when(service.update(Mockito.eq(1L), Mockito.any()))
                .thenReturn(updated);

        mockMvc.perform(put("/api/etudiants/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("New"));
    }

    @Test
    void update_shouldReturn400_whenNotFound() throws Exception {
        Mockito.when(service.update(Mockito.eq(99L), Mockito.any()))
                .thenThrow(new RuntimeException("Etudiant not found"));

        mockMvc.perform(put("/api/etudiants/99")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new Etudiant())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_shouldReturn200() throws Exception {
        Mockito.doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/etudiants/1"))
                .andExpect(status().isOk());
    }
}
