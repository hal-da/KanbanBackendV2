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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final JWTIssuer jwtIssuer;
    private final UserService userService;
    private final PublicUserMapper publicUserMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String welcome(){
        return "Login to Kanbantastisch API";
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginDto) {
        UserEntity user = userService.findByEmail(loginDto.getEmail());
        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            throw new RuntimeException("Email or password is not correct");
        }
        String token = jwtIssuer.issueToken(
                user.getId(),
                loginDto.getEmail()
        );
        return LoginResponseDto.builder().token(token).build();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PublicUserDto register(@RequestBody @Valid RegisterDto registerDto)
            throws EmailExistsException, PasswordsNotSimilarException {
        if(!registerDto.getPassword1().equals(registerDto.getPassword2())){
            throw new PasswordsNotSimilarException("Passwords are not similar");
        }
        String encodedPW = passwordEncoder.encode(registerDto.getPassword1());
        UserEntity user = new UserEntity(registerDto.getUserName(), encodedPW, registerDto.getEmail());
        UserEntity registeredUser = userService.register(user);
        return publicUserMapper.toPublicUserDto(registeredUser);
    }
}
