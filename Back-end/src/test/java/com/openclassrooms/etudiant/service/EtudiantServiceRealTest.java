package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.Etudiant;
import com.openclassrooms.etudiant.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EtudiantServiceRealTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantService service;

    // -----------------------------
    // FIND ALL
    // -----------------------------
    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(
                Arrays.asList(
                        new Etudiant(1L, "John", "Doe", "login"),
                        new Etudiant(2L, "Jane", "Smith", "jsmith")
                )
        );

        var list = service.findAll();

        assertThat(list).hasSize(2);
        verify(repository).findAll();
    }

    // -----------------------------
    // FIND BY ID
    // -----------------------------
    @Test
    void findById_shouldReturnEtudiant() {
        Etudiant e = new Etudiant(1L, "John", "Doe", "login");
        when(repository.findById(1L)).thenReturn(Optional.of(e));

        var result = service.findById(1L);

        assertThat(result.getFirstName()).isEqualTo("John");
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(99L));
    }

    // -----------------------------
    // CREATE
    // -----------------------------
    @Test
    void create_shouldSaveEtudiant() {
        Etudiant e = new Etudiant(null, "John", "Doe", "login");
        Etudiant saved = new Etudiant(1L, "John", "Doe", "login");

        when(repository.save(any(Etudiant.class))).thenReturn(saved);

        var result = service.create(e);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository).save(any(Etudiant.class));
    }

    // -----------------------------
    // UPDATE
    // -----------------------------
    @Test
    void update_shouldUpdateEtudiant() {
        Etudiant existing = new Etudiant(1L, "Old", "Name", "oldLogin");
        Etudiant updated = new Etudiant(1L, "New", "Name", "newLogin");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Etudiant.class))).thenReturn(updated);

        var result = service.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("New");
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        service.delete(99L);
        verify(repository).deleteById(99L);
    }

    // -----------------------------
    // DELETE
    // -----------------------------
    @Test
    void delete_shouldCallRepository() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldThrow_whenNotFound() {
        when(repository.existsById(99L)).thenReturn(false);
        
        service.delete(99L);
        
        verify(repository).deleteById(99L);
    }
}
