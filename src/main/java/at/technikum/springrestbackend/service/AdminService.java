package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.model.Role;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void createAdminIfNotExists() {
        if (!userRepository.existsByUsername("admin")) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setEmail("admin@admin");
            admin.setCca3("BAN");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }

    public void createSYSTEMIfNotExists() {
        if (!userRepository.existsByUsername("SYSTEM")) {
            UserEntity system = new UserEntity();
            system.setUsername("SYSTEM");
            system.setEmail("SYSTEM@SYSTEM");
            system.setCca3("BAN");
            system.setRole(Role.ADMIN);
            system.setId("SYSTEM");
            userRepository.save(system);
        }
    }
}
