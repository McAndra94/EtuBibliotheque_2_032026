package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.dto.LoginRequestDTO;
import com.openclassrooms.etudiant.dto.RegisterDTO;
import com.openclassrooms.etudiant.entities.User;
import com.openclassrooms.etudiant.service.UserService;
import com.openclassrooms.etudiant.service.JwtService;
import com.openclassrooms.etudiant.mapper.UserDtoMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    private static final String URL = "/api/register";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDtoMapper userDtoMapper;

    // REGISTER TESTS
    @Test
    void registerUserWithoutRequiredData() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(registerDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerAlreadyExistUser() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFirstName("John");
        registerDTO.setLastName("Doe");
        registerDTO.setLogin("login");
        registerDTO.setPassword("password");

        Mockito.when(userDtoMapper.toEntity(Mockito.any(RegisterDTO.class)))
               .thenReturn(new User());

        Mockito.doThrow(new RuntimeException("User already exists"))
               .when(userService).register(Mockito.any(User.class));

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(registerDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registerUserSuccessful() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setFirstName("John");
        registerDTO.setLastName("Doe");
        registerDTO.setLogin("login");
        registerDTO.setPassword("password");

        Mockito.when(userDtoMapper.toEntity(Mockito.any(RegisterDTO.class)))
               .thenReturn(new User());

        mockMvc.perform(post(URL)
                        .content(objectMapper.writeValueAsString(registerDTO))
                        .contentType("application/json"))
                .andExpect(status().isCreated());
    }

    // LOGIN TESTS
    @Test
    void loginSuccessful() throws Exception {
    LoginRequestDTO dto = new LoginRequestDTO();
    dto.setLogin("login");
    dto.setPassword("password");

    Mockito.when(userService.login("login", "password"))
           .thenReturn("TOKEN");

    mockMvc.perform(post("/api/login")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk());
}

@Test
void loginWrongLogin_shouldReturn400() throws Exception {
    LoginRequestDTO dto = new LoginRequestDTO();
    dto.setLogin("wrong");
    dto.setPassword("password");

    Mockito.when(userService.login("wrong", "password"))
           .thenThrow(new RuntimeException("Invalid credentials"));

    mockMvc.perform(post("/api/login")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
}

@Test
void loginWrongPassword_shouldReturn400() throws Exception {
    LoginRequestDTO dto = new LoginRequestDTO();
    dto.setLogin("login");
    dto.setPassword("wrong");

    Mockito.when(userService.login("login", "wrong"))
           .thenThrow(new RuntimeException("Invalid credentials"));

    mockMvc.perform(post("/api/login")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isBadRequest());
}
}