package com.openclassrooms.etudiant.mapper;

import com.openclassrooms.etudiant.dto.RegisterDTO;
import com.openclassrooms.etudiant.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-21T19:52:44+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public User toEntity(RegisterDTO registerDTO) {
        if ( registerDTO == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( registerDTO.getFirstName() );
        user.setLastName( registerDTO.getLastName() );
        user.setLogin( registerDTO.getLogin() );
        user.setPassword( registerDTO.getPassword() );

        return user;
    }
}
