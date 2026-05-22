package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.Etudiant;
import com.openclassrooms.etudiant.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantService service;

    // CREATE
    @Test
    void create_shouldSaveEtudiant() {
        Etudiant input = new Etudiant(null, "John", "Doe", "login");
        Etudiant saved = new Etudiant(1L, "John", "Doe", "login");

        when(repository.save(any(Etudiant.class))).thenReturn(saved);

        Etudiant result = service.create(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository, times(1)).save(input);
    }

    // FIND ALL
    @Test
    void findAll_shouldReturnList() {
        List<Etudiant> list = Arrays.asList(
                new Etudiant(1L, "John", "Doe", "login"),
                new Etudiant(2L, "Jane", "Smith", "jsmith"));

        when(repository.findAll()).thenReturn(list);

        List<Etudiant> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    // FIND BY ID
    @Test
    void findById_shouldReturnEtudiant() {
        Etudiant e = new Etudiant(1L, "John", "Doe", "login");
        when(repository.findById(1L)).thenReturn(Optional.of(e));

        Etudiant result = service.findById(1L);

        assertThat(result.getFirstName()).isEqualTo("John");
        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Etudiant not found");
    }

    // UPDATE
    @Test
    void update_shouldModifyEtudiant() {
        Etudiant existing = new Etudiant(1L, "Old", "Name", "oldLogin");
        Etudiant updated = new Etudiant(null, "New", "Name", "newLogin");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Etudiant.class))).thenReturn(existing);

        Etudiant result = service.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("New");
        assertThat(result.getLastName()).isEqualTo("Name"); // Reste "Name" d'après ton implémentation de service
        verify(repository).save(existing);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, new Etudiant()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Etudiant not found");
    }

    // DELETE
    @Test
    void delete_shouldCallRepository() {
        service.delete(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}