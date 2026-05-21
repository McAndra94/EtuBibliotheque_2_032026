package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.entities.User;
import com.openclassrooms.etudiant.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceRealTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void register_shouldThrow_whenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.register(null));
    }

    @Test
    void register_shouldThrow_whenLoginExists() {
        User existing = new User();
        existing.setLogin("alex");

        when(userRepository.findByLogin("alex")).thenReturn(Optional.of(existing));

        User newUser = new User();
        newUser.setLogin("alex");

        assertThrows(IllegalArgumentException.class, () -> userService.register(newUser));
    }

    @Test
    void register_shouldEncodePassword_andSaveUser() {
        User newUser = new User();
        newUser.setLogin("alex");
        newUser.setPassword("pwd");

        when(userRepository.findByLogin("alex")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pwd")).thenReturn("ENCODED");

        userService.register(newUser);

        verify(userRepository).save(argThat(u ->
                u.getLogin().equals("alex") &&
                u.getPassword().equals("ENCODED")
        ));
    }

    @Test
    void login_shouldThrow_whenUserNotFound() {
        when(userRepository.findByLogin("alex")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.login("alex", "pwd"));
    }

    @Test
    void login_shouldThrow_whenPasswordInvalid() {
        User user = new User();
        user.setLogin("alex");
        user.setPassword("ENCODED");

        when(userRepository.findByLogin("alex")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pwd", "ENCODED")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.login("alex", "pwd"));
    }

    @Test
    void login_shouldReturnToken_whenCredentialsValid() {
        User user = new User();
        user.setLogin("alex");
        user.setPassword("ENCODED");

        when(userRepository.findByLogin("alex")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pwd", "ENCODED")).thenReturn(true);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("TOKEN");

        String token = userService.login("alex", "pwd");

        assertThat(token).isEqualTo("TOKEN");
    }
}
