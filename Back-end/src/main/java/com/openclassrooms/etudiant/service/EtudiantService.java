package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.Etudiant;
import com.openclassrooms.etudiant.repository.EtudiantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository repository;
    // Create
    public Etudiant create(Etudiant etudiant) {
        Etudiant saved = repository.save(etudiant);
        log.info("Created Etudiant id={}", saved.getId());
        return saved;
    }
    // Read (all students)
    public List<Etudiant> findAll() {
        return repository.findAll();
    }
    // Read (one student)
    public Etudiant findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant not found"));
    }
    // Update
    public Etudiant update(Long id, Etudiant updated) {
        Etudiant etudiant = findById(id);
        etudiant.setFirstName(updated.getFirstName());
        etudiant.setLastName(updated.getLastName());
        return repository.save(etudiant);
    }
    // Delete
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
