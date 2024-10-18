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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final JWTIssuer jwtIssuer;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void delete(String id) {
        userRepository.deleteById(id);
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public UserEntity findById(String id, UserEntity user) {
        if (!user.getId().equals(id)) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public UserEntity findByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findAll(UserPrincipal userPrincipal) {
        if (!userPrincipal.isAdmin()) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        return userRepository.findAll();
    }

    public LoginResponseDto loginUser(@Valid LoginRequestDto loginDto, UserEntity user) {
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordOrEmailWrongException("Email or password is not correct");
        }
        String token = jwtIssuer.issueToken(user.getId(), loginDto.getEmail(), user.getRole());
        return LoginResponseDto.builder().token(token).build();
    }

    public UserEntity registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword1().equals(registerDto.getPassword2())) {
            throw new PasswordsNotSimilarException("Passwords are not similar");
        }
        UserEntity userEntity = userRepository.findByEmail(registerDto.getEmail());
        if (userEntity != null) {
            throw new EmailExistsException("User with email " + registerDto.getEmail() + " already exists");
        }
        String encodedPW = passwordEncoder.encode(registerDto.getPassword1());
        Date now = new Date();
        System.out.println("Registering user with cca3 UserService " + registerDto.getCca3());
        UserEntity user = new UserEntity
                .Builder()
                .withEmail(registerDto.getEmail())
                .withPassword(encodedPW)
                .withUsername(registerDto.getUserName())
                .withCreatedAt(now)
                .withLastChangeAt(now)
                .withCca3(registerDto.getCca3())
                .build();

        System.out.println("Registered user with cca3 UserService " + user.getCca3());
        return userRepository.save(user);
    }

    public void addUserImageUrlToUser(String imageUrl, String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));
        user.setImageUrl(imageUrl);
        System.out.println("User image url in userService " + user.getImageUrl());
        userRepository.save(user);

    }

    public UserEntity update(String id, UpdateUserDto updateUserDto, UserEntity requestUser) {
        if (!id.equals(requestUser.getId()) && !requestUser.isAdmin()) {
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        UserEntity userToChange = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        userToChange.setLastChangeAt(new Date());
        userToChange.setLastChangeBy(requestUser);
        userToChange.setEmail(updateUserDto.getEmail());
        userToChange.setImageUrl(updateUserDto.getImageUrl());
        userToChange.setUsername(updateUserDto.getUserName());
        userToChange.setCca3(updateUserDto.getCca3());

        return userRepository.save(userToChange);
    }
}
