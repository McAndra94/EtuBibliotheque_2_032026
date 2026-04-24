package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.Etudiant;
import com.openclassrooms.etudiant.repository.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    private EtudiantRepository repository;
    private EtudiantService service;

    @BeforeEach
    void setUp() {
        repository = mock(EtudiantRepository.class);
        service = new EtudiantService(repository);
    }

    @Test
    void create_shouldSaveEtudiant() {
        Etudiant input = new Etudiant(null, "John", "Doe", "login");
        Etudiant saved = new Etudiant(1L, "John", "Doe", "login");

        when(repository.save(input)).thenReturn(saved);

        Etudiant result = service.create(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(repository, times(1)).save(input);
    }

    @Test
    void findAll_shouldReturnList() {
        List<Etudiant> list = Arrays.asList(
                new Etudiant(1L, "John", "Doe", "login"),
                new Etudiant(2L, "Tony", "Debo", "TonyD")
        );

        when(repository.findAll()).thenReturn(list);

        List<Etudiant> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

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

    @Test
    void update_shouldModifyEtudiant() {
        Etudiant existing = new Etudiant(1L, "Old", "Name", "oldLogin");
        Etudiant updated = new Etudiant(null, "New", "Name", "newLogin");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Etudiant result = service.update(1L, updated);

        assertThat(result.getFirstName()).isEqualTo("New");
        assertThat(result.getLastName()).isEqualTo("Name");

        verify(repository).save(existing);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, new Etudiant()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Etudiant not found");
    }

    @Test
    void delete_shouldCallRepository() {
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
