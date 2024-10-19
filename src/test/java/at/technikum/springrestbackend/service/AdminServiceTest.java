package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
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
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAdminIfNotExists_whenAdminExists() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(true);

        // Act
        adminService.createAdminIfNotExists();

        // Assert
        verify(userRepository, never()).save(any(UserEntity.class)); // Admin should not be saved again
    }

    @Test
    void testCreateAdminIfNotExists_whenAdminDoesNotExist() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(passwordEncoder.encode("admin")).thenReturn("encodedAdminPassword");

        // Act
        adminService.createAdminIfNotExists();

        // Assert
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        UserEntity savedAdmin = userCaptor.getValue();

        // Validate admin properties
        assertEquals("admin", savedAdmin.getUsername());
        assertEquals("admin@admin", savedAdmin.getEmail());
        assertEquals("encodedAdminPassword", savedAdmin.getPassword());
        assertEquals(Role.ADMIN, savedAdmin.getRole());

        // Verify password encoding
        verify(passwordEncoder).encode("admin");
    }
}

