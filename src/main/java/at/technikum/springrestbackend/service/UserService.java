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

    public void delete(String id){
        userRepository.deleteById(id);
    }

    public UserEntity findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
    public UserEntity findById(String id, UserEntity user) {
        if(!user.getId().equals(id)){
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public UserEntity findByEmail(String email){
        UserEntity user =  userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    public UserEntity register(UserEntity user) throws EmailExistsException {
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        if(userEntity != null){
            throw new EmailExistsException("User with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }


    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findAll(UserPrincipal userPrincipal) {
        if(!userPrincipal.isAdmin()){
            throw new UserNotEnoughPrivileges("You are not authorized to access this resource");
        }
        return userRepository.findAll();
    }

    public LoginResponseDto loginUser(@Valid LoginRequestDto loginDto, UserEntity user) {
        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new RuntimeException("Email or password is not correct");
        }
        String token = jwtIssuer.issueToken(
                user.getId(),
                loginDto.getEmail(),
                user.getRole()
        );
        return LoginResponseDto.builder().token(token).build();
    }

    public UserEntity registerUser(RegisterDto registerDto){
        if(!registerDto.getPassword1().equals(registerDto.getPassword2())){
            throw new PasswordsNotSimilarException("Passwords are not similar");
        }
        String encodedPW = passwordEncoder.encode(registerDto.getPassword1());
        Date now = new Date();
        UserEntity user = new UserEntity.Builder()
                .withEmail(registerDto.getEmail())
                .withPassword(encodedPW)
                .withUsername(registerDto.getUserName())
                .withCreatedAt(now)
                .withLastChangeAt(now)
                .build();
        return register(user);
    }
}
