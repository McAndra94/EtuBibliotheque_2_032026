package com.openclassrooms.etudiant.mapper;

import com.openclassrooms.etudiant.dto.RegisterDTO;
import com.openclassrooms.etudiant.entities.User;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserDtoMapperTest {

    @Test
    void toEntity_shouldMapRegisterDTO_toUser() {
        UserDtoMapper mapper = new UserDtoMapperImpl();

        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setLogin("login");
        dto.setPassword("pwd");

        User user = mapper.toEntity(dto);

        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getLogin()).isEqualTo("login");
        assertThat(user.getPassword()).isEqualTo("pwd");

        assertThat(user.getId()).isNull();
        assertThat(user.getCreated_at()).isNull();
        assertThat(user.getUpdated_at()).isNull();
    }
}