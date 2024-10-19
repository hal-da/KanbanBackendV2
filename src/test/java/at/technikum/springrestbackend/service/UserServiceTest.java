package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.dto.RegisterDto;
import at.technikum.springrestbackend.dto.UpdateUserDto;
import at.technikum.springrestbackend.exception.*;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import at.technikum.springrestbackend.security.JWTIssuer;
import at.technikum.springrestbackend.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete_Success() {
        // Arrange
        String userId = "123";

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindById_Success() {
        // Arrange
        String userId = "123";
        UserEntity user = new UserEntity();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = userService.findById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_EntityNotFound() {
        // Arrange
        String userId = "123";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByEmail_Success() {
        // Arrange
        String email = "test@example.com";
        UserEntity user = new UserEntity();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        UserEntity result = userService.findByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_UsernameNotFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.findByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }
    @Test
    void testRegisterUser_EmailExists() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("test@example.com");
        registerDto.setPassword1("password");
        registerDto.setPassword2("password");

        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(new UserEntity());

        // Act & Assert
        assertThrows(EmailExistsException.class, () -> userService.registerUser(registerDto));
        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
    }

    @Test
    void testLoginUser_WrongPassword() {
        // Arrange
        LoginRequestDto loginDto = LoginRequestDto.builder()
                .email("test@example.com")
                .password("password")
                .build();

        UserEntity user = new UserEntity();
        user.setPassword("encodedPassword");

        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(PasswordOrEmailWrongException.class, () -> userService.loginUser(loginDto, user));
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), user.getPassword());
    }

   /* @Test
    void testUpdate_Success() {
        // Arrange
        String userId = "123";
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setEmail("updated@example.com");
        updateUserDto.setUserName("UpdatedUser");
        updateUserDto.setCca3("USA");

        UserEntity requestUser = new UserEntity();
        requestUser.setId(userId);
        requestUser.setAdmin(true);

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        UserEntity result = userService.update(userId, updateUserDto, requestUser);

        // Assert
        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
        assertEquals("UpdatedUser", result.getUsername());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }*/

    @Test
    void testFindAll_Success() {
        // Arrange
        List<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserEntity> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }
}

