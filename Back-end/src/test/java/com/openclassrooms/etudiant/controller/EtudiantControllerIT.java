package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.entities.Etudiant;
import com.openclassrooms.etudiant.repository.EtudiantRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EtudiantControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EtudiantRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnEtudiant() throws Exception {
        Etudiant e = new Etudiant(null, "John", "Doe", "login");

        mockMvc.perform(post("/api/etudiants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(e)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        repository.save(new Etudiant(null, "John", "Doe", "login"));
        repository.save(new Etudiant(null, "Tony", "Debo", "TonyD"));

        mockMvc.perform(get("/api/etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getOne_shouldReturnEtudiant() throws Exception {
        Etudiant saved = repository.save(new Etudiant(null, "John", "Doe", "login"));

        mockMvc.perform(get("/api/etudiants/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void update_shouldReturnUpdatedEtudiant() throws Exception {
        Etudiant saved = repository.save(new Etudiant(null, "Old", "Name", "oldLogin"));

        Etudiant updated = new Etudiant(null, "New", "Name", "newLogin");

        mockMvc.perform(put("/api/etudiants/" + saved.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("New"));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        Etudiant saved = repository.save(new Etudiant(null, "John", "Doe", "login"));

        mockMvc.perform(delete("/api/etudiants/" + saved.getId()))
                .andExpect(status().isNoContent());
    }
}
