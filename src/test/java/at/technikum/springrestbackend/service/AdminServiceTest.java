package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAdminIfNotExists_AdminDoesNotExist() {
        // Arrange: Mock that admin does not exist
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(passwordEncoder.encode("admin")).thenReturn("encodedAdminPassword");

        // Act
        adminService.createAdminIfNotExists();

        // Assert
        verify(userRepository, times(1)).existsByUsername("admin");
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(passwordEncoder, times(1)).encode("admin");
    }

    @Test
    void testCreateAdminIfNotExists_AdminExists() {
        // Arrange: Mock that admin already exists
        when(userRepository.existsByUsername("admin")).thenReturn(true);

        // Act
        adminService.createAdminIfNotExists();

        // Assert
        verify(userRepository, times(1)).existsByUsername("admin");
        verify(userRepository, times(0)).save(any(UserEntity.class)); // Should not save admin again
        verify(passwordEncoder, times(0)).encode(anyString()); // Should not encode password
    }
}

