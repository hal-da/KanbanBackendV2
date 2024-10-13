package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.dto.PublicUserDto;
import at.technikum.springrestbackend.dto.RegisterDto;
import at.technikum.springrestbackend.exception.EmailExistsException;
import at.technikum.springrestbackend.exception.PasswordsNotSimilarException;
import at.technikum.springrestbackend.mapper.PublicUserMapper;
import at.technikum.springrestbackend.model.UserEntity;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final PublicUserMapper publicUserMapper;

    @GetMapping("/login")
    public String welcome(){
        return "Login to Kanbantastisch API";
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginDto) {
        UserEntity user = userService.findByEmail(loginDto.getEmail());
        return userService.loginUser(loginDto, user);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PublicUserDto register(@RequestBody @Valid RegisterDto registerDto)
            throws EmailExistsException, PasswordsNotSimilarException {
        return publicUserMapper.toPublicUserDto(userService.registerUser(registerDto));
    }

    @GetMapping("/whoami")
    public PublicUserDto whoAmI(@AuthenticationPrincipal UserPrincipal userPrincipal){
        UserEntity user = userService.findByEmail(userPrincipal.getEmail());
        return publicUserMapper.toPublicUserDto(user);
    }
}
