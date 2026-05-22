package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.dto.LoginRequestDTO;
import com.openclassrooms.etudiant.dto.RegisterDTO;
import com.openclassrooms.etudiant.entities.User;
import com.openclassrooms.etudiant.repository.UserRepository;
import com.openclassrooms.etudiant.service.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;

    @BeforeEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void registerUser_shouldReturn201() throws Exception {
        String dynamicLogin = "user_" + System.currentTimeMillis();

        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setLogin(dynamicLogin);
        dto.setPassword("password");

        mockMvc.perform(post("/api/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void registerUser_shouldReturn400_whenAlreadyExists() throws Exception {
        String duplicateLogin = "duplicate_" + System.currentTimeMillis();

        User existing = new User();
        existing.setLogin(duplicateLogin);
        existing.setPassword(passwordEncoder.encode("encoded"));
        existing.setFirstName("John");
        existing.setLastName("Doe");
        userRepository.save(existing);

        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setLogin(duplicateLogin);
        dto.setPassword("password");

        mockMvc.perform(post("/api/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_shouldReturn200() throws Exception {
        String loginName = "login_" + System.currentTimeMillis();

        User user = new User();
        user.setLogin(loginName);
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setLogin(loginName);
        dto.setPassword("password");

        when(jwtService.generateToken(any())).thenReturn("TOKEN");

        mockMvc.perform(post("/api/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void login_shouldReturn400_whenWrongPassword() throws Exception {
        String loginWrongName = "loginWrong_" + System.currentTimeMillis();

        User user = new User();
        user.setLogin(loginWrongName);
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setLogin(loginWrongName);
        dto.setPassword("wrong");

        mockMvc.perform(post("/api/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}