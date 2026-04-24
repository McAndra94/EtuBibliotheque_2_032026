package com.openclassrooms.etudiant.controller;

import com.openclassrooms.etudiant.entities.Etudiant;
import com.openclassrooms.etudiant.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

    private final EtudiantService service;
    // Create
    @PostMapping
    public Etudiant create(@RequestBody Etudiant etudiant) {
        return service.create(etudiant);
    }
    // Read (all)
    @GetMapping
    public List<Etudiant> getAll() {
        return service.findAll();
    }
    // Read (one)
    @GetMapping("/{id}")
    public Etudiant getOne(@PathVariable Long id) {
        return service.findById(id);
    }
    // Update
    @PutMapping("/{id}")
    public Etudiant update(@PathVariable Long id, @RequestBody Etudiant etudiant) {
        return service.update(id, etudiant);
    }
    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
