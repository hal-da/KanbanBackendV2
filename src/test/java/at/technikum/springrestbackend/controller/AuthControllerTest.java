
package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.dto.RegisterDto;
import at.technikum.springrestbackend.exception.EmailExistsException;
import at.technikum.springrestbackend.exception.PasswordsNotSimilarException;
import at.technikum.springrestbackend.mapper.PublicUserMapper;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.JWTIssuer;
import at.technikum.springrestbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JWTIssuer jwtIssuer;

    @Mock
    private UserService userService;

    @Mock
    private PublicUserMapper publicUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testWelcome() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login to Kanbantastisch API"));
    }

    @Test
    void testLoginSuccess() throws Exception {

        UserEntity user = new UserEntity("username", "encodedPassword", "user@example.com");
        user.setId("user-id");
        String token = "jwt-token";

        when(userService.findByEmail(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtIssuer.issueToken(anyString(), anyString())).thenReturn(token);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"email\":\"user@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void testLoginFailure() throws Exception {
        UserEntity user = new UserEntity("username", "encodedPassword", "user@example.com");

        when(userService.findByEmail(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"email\":\"user@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Email or password is not correct"));
    }

    @Test
    void testRegisterSuccess() throws Exception {
        UserEntity user = new UserEntity("username", "encodedPassword", "user@example.com");
        user.setId("user-id");
        PublicUserDto publicUserDto = new PublicUserDto("user-id", "username");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userService.registerUser(any(UserEntity.class))).thenReturn(user);
        when(publicUserMapper.toPublicUserDto(any(UserEntity.class))).thenReturn(publicUserDto);

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"userName\":\"username\", \"password1\":\"password1\", \"password2\":\"password1\", \"email\":\"user@example.com\"}"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value("user-id"))
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void testRegisterPasswordMismatch() throws Exception {
        RegisterDto registerDto = new RegisterDto("username", "password1", "password2", "user@example.com");

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content("{\"userName\":\"username\", \"password1\":\"password1\", \"password2\":\"password2\", \"email\":\"user@example.com\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Passwords are not similar"));
    }
}


