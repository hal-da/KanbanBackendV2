package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.dto.RegisterDto;
import at.technikum.springrestbackend.exception.EmailExistsException;
import at.technikum.springrestbackend.exception.PasswordsNotSimilarException;
import at.technikum.springrestbackend.exception.UserNotEnoughPrivileges;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import at.technikum.springrestbackend.security.JWTIssuer;
import at.technikum.springrestbackend.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private JWTIssuer jwtIssuer;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity("username", "encodedPassword", "test@example.com");
        user.setId("12345");
    }

    @Test
    // Hier wird ueberprueft, ob der Benutzer geloscht ist, und ob die Methode deletById einmal aufgerufen ist.
    void testDelete() {
        doNothing().when(userRepository).deleteById("12345");
        userService.delete("12345");
        verify(userRepository, times(1)).deleteById("12345");
    }

    @Test
    void testFindById_Success() {
        when(userRepository.findById("12345")).thenReturn(Optional.of(user));
        UserEntity foundUser = userService.findById("12345");
        assertEquals("12345", foundUser.getId());
        assertEquals("test@example.com", foundUser.getEmail());
    }


    @Test
    void testFindByEmail_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        UserEntity result = userService.findByEmail("test@example.com");
        assertEquals("test@example.com", result.getEmail());
    }


    @Test
    void testRegister_Success() throws EmailExistsException {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        UserEntity registeredUser = userService.register(user);
        assertEquals("test@example.com", registeredUser.getEmail());
    }

    @Test
    void testRegister_EmailExists() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        EmailExistsException exception = assertThrows(EmailExistsException.class, () -> {
            userService.register(user);
        });
        assertEquals("User with email test@example.com already exists", exception.getMessage());
    }

    @Test
    void testRegisterUser_Success() {
        RegisterDto registerDto = new RegisterDto("test@example.com", "password1", "password1", "username");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity registeredUser = userService.registerUser(registerDto);
        assertEquals("test@example.com", registeredUser.getEmail());
    }

}
